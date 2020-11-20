package com.piancloud.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @description: 这个过滤器解决body不能重复读的问题，为后续的XssRequestGlobalFilter重写post|put请求的body做准备
 * @author: chendong
 * @create: 2020/11/2 17:26
 */
@Component
public class CacheBodyGlobalFilter  implements Ordered, GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpMethod method = exchange.getRequest().getMethod();
        String contentType = exchange.getRequest().getHeaders().getFirst(HttpHeaders.CONTENT_TYPE);
        if (method == HttpMethod.POST || method == HttpMethod.PUT) {
            if (MediaType.APPLICATION_FORM_URLENCODED_VALUE.equalsIgnoreCase(contentType)
                || MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(contentType)
                || MediaType.APPLICATION_JSON_UTF8_VALUE.equals(contentType)) {
                return DataBufferUtils.join(exchange.getRequest().getBody())
                    .flatMap(dataBuffer -> {
                        DataBufferUtils.retain(dataBuffer);
                        Flux<DataBuffer> cachedFlux = Flux
                            .defer(() -> Flux.just(dataBuffer.slice(0, dataBuffer.readableByteCount())));
                        ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(
                            exchange.getRequest()) {
                            @Override
                            public Flux<DataBuffer> getBody() {
                                return cachedFlux;
                            }
                        };
                        return chain.filter(exchange.mutate().request(mutatedRequest).build());
                    });
            }

        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}

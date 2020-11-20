package com.piancloud.gateway.filter;

import com.piancloud.gateway.utils.XssCleanRuleUtils;
import java.nio.charset.StandardCharsets;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * @description: 重写Response，防止xss攻击
 * @author: chendong
 * @create: 2020/11/2 17:29
 */
@Component
public class XssResponseGlobalFilter implements Ordered, GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse originalResponse = exchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {

                String contentType = getDelegate().getHeaders().getFirst(HttpHeaders.CONTENT_TYPE);
                Boolean flag = MediaType.APPLICATION_JSON_VALUE.equals(contentType) || MediaType.APPLICATION_JSON_UTF8_VALUE.equals(contentType);
                if (body instanceof Flux && flag) {
                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                    return super.writeWith(fluxBody.buffer().map(dataBuffer -> {
                        //DefaultDataBufferFactory join 乱码的问题解决
                        DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
                        DataBuffer join = dataBufferFactory.join(dataBuffer);
                        byte[] content = new byte[join.readableByteCount()];
                        join.read(content);
                        //释放掉内存
                        DataBufferUtils.release(join);
                        String result = new String(content, StandardCharsets.UTF_8);
                        //result就是response的值，对result进行去XSS
                        result = XssCleanRuleUtils.xssClean(result);
                        byte[] uppedContent = new String(result.getBytes(), StandardCharsets.UTF_8).getBytes(StandardCharsets.UTF_8);
                        return bufferFactory.wrap(uppedContent);
                    }));
                }
                return super.writeWith(body);
            }
        };
        return chain.filter(exchange.mutate().response(decoratedResponse).build());
    }

    @Override
    public int getOrder() {
        return -50;
    }
}
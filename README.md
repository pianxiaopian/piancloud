# piancloud

## 临时起意 在东锅带领下 学习搭建一下后端框架 整一套微服务耍耍

## 平台简介

* 采用前后端分离的模式，微服务版本前端。
* 后端采用Spring Boot、Spring Cloud & Alibaba。
* 注册中心、配置中心选型Nacos，登录权限认证使用Security + OAuth 2.0 + Redis 。
* 流量控制框架选型Sentinel。
* 分布式事务架构选型Seata。
* db与redis数据同步架构选型Canal + ZooKeeper + Kafka
* 


## 系统模块

~~~
piancloud     
├── piancloud-api              // 接口工程
├── piancloud-common         // 公共模块工程
├── piancloud-gateway             // 服务网关[8002]
├── piancloud-job           // 调度工程
├── piancloud-rest          // rest工程
├── piancloud-springboot-admin          //监控服务工程[8001]
├──pom.xml                // 公共依赖
~~~


- 注意事项

# 090-后台-SpringCloud-后端

config包里的类处理跨域，filter包里的类处理权限认证，handler包里的类处理异常。跨域的哪个类写的好乱，不用的包也不清除。老师不讲一下，明天我就忘了，我太难了

## 1.配置application.properties

1. 配置nacos
2. 配置需要使用的服务

![image-20210320094702806](https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/image-20210320094702806.png)

```properties
# 服务端口
server.port=8222
# 服务名
spring.application.name=service-gateway

# nacos服务地址
spring.cloud.nacos.discovery.server-addr=127.0.0.1:8848

#使用服务发现路由
spring.cloud.gateway.discovery.locator.enabled=true

#配置service-edu服务
spring.cloud.gateway.routes[0].id=service-edu
spring.cloud.gateway.routes[0].uri=lb://service-edu
spring.cloud.gateway.routes[0].predicates= Path=/eduservice/**
```

## 2.跨域问题

* @CrossOrigin和SpringCloud的跨域配置类，只能配置一个，可以把@CrossOrigin删除

## 3.前端的后台系统

* 访问地址变成SpringCloud的地址
* config/dev.env.js

![image-20210320094858005](https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/image-20210320094858005.png)
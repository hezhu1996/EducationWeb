# 部署服务（Deployment）

## 启动

1. nginx（不需要，SpringCloud gateway 代替）
2. nacos
3. redis（依据需要，存储短信验证码等）
4. 前台系统，必须登录，才能看到视频内容
5. 后台服务
   1. Edu：8001
   2. Oss：8002
   3. Vod：8003
   4. Cms：8004
   5. Msm：8005
   6. Orders：8007
   7. Sta：8008
   8. ServiceAcl：8009
   9. Ucenter：8150
   10. ApiGateway：8222

## 部署前端

### IDEA:

* `service_edu` 中把 `logback-spring.xml` 删除

* `application-prod.properties`

  * 修改 `mysql` 数据库链接，自己起别名（用于docker间通讯）

  * `redis`链接，自己起别名（用于docker间通讯）

  * Nacos 连接，公网IP

  * 服务端口统一设置为 8080（docker容器内）

  * ```properties
    # 服务端口
    server.port=8080
    
    # 服务名：Nacos中显示（不要"_"）
    spring.application.name=service-edu
    
    # 环境设置：dev、test、prod
    #spring.profiles.active=prod
    
    # mysql数据库连接
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.datasource.url=jdbc:mysql://mysql8:3306/guli?serverTimezone=Asia/Shanghai
    spring.datasource.username=root
    spring.datasource.password=root
    
    #mybatis日志
    mybatis-plus.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl
    mybatis-plus.mapper-locations=classpath:com/hezhu/edu/mapper/xml/*.xml
    
    #返回json的全局时间格式
    spring.jackson.date-format=yyyy-MM-dd HH:mm:ss
    spring.jackson.time-zone=GMT+8
    
    # 设置日志级别
    logging.level.root=info
    
    #Nacos
    spring.cloud.nacos.discovery.server-addr=47.94.174.79:8848
    
    #开启熔断机制
    feign.hystrix.enabled=true
    # 设置hystrix超时时间，默认1000ms
    hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=6000
    feign.client.config.default.connect-timeout=20000
    feign.client.config.default.read-timeout=20000 
    ```

### Docker 设置

* Dockerfile

  * ```dockerfile
    FROM openjdk:11
    EXPOSE 8080
    
    VOLUME /tmp
    ADD *.jar /app.jar
    RUN bash -c 'touch /app.jar'
    ENTRYPOINT ["java","-jar","/app.jar","--spring.profiles.active=prod"]
    ```

* shell 命令 开启 docker 容器

  * ```shell
    # api_gateway
    docker build -f Dockerfile -t api_gateway .
    docker run -p 8222:8080 --name api_gateway -it api_gateway
    docker update api_gateway --restart=always
    
    # service_edu
    docker build -f Dockerfile -t service_edu .
    docker run -p 8001:8080 --name service_edu --link mysql:mysql8 -it service_edu
    docker update service_edu --restart=always
    
    # service_oss
    docker build -f Dockerfile -t service_oss .
    docker run -p 8002:8080 --name service_oss -it service_oss
    docker update service_oss --restart=always
    
    # service_vod
    docker build -f Dockerfile -t service_vod .
    docker run -p 8003:8080 --name service_vod -it service_vod
    docker update service_vod --restart=always
    
    # service_cms
    docker build -f Dockerfile -t service_cms .
    docker run -p 8004:8080 --name service_cms --link mysql:mysql8 --link redis:redis -it service_cms
    docker update service_cms --restart=always
    
    # service_msm
    docker build -f Dockerfile -t service_msm .
    docker run -p 8005:8080 --name service_msm --link mysql:mysql8 --link redis:redis -it service_msm
    docker update service_msm --restart=always
    
    # service_order
    docker build -f Dockerfile -t service_order .
    docker run -p 8007:8080 --name service_order --link mysql:mysql8 -it service_order
    docker update service_order --restart=always
    
    # service_statistic
    docker build -f Dockerfile -t service_statistic .
    docker run -p 8008:8080 --name service_statistic --link mysql:mysql8 -it service_statistic
    docker update service_statistic --restart=always
    
    # service_ucenter
    docker build -f Dockerfile -t service_ucenter .
    docker run -p 8150:8080 --name service_ucenter --link mysql:mysql8 --link redis:redis -it service_ucenter
    docker update service_ucenter --restart=always
    
    # service_acl
    docker build -f Dockerfile -t service_acl .
    docker run -p 8009:8080 --name service_acl --link mysql:mysql8 --link redis:redis -it service_acl
    docker update service_acl --restart=always
    
    docker exec -it service_acl /bin/bash
    ```

## NUXT:

- NuxtJS服务端部署过程：https://blog.csdn.net/u012757419/article/details/99459094

- NuxtJS 文档：https://zh.nuxtjs.org/

- Nuxt 部署成功

  - `npm run build`

  - `npm start`

  - `package.json` 这里最先一定改成 “0.0.0.0”

  - ```json
    {
      "name": "name",
      "version": "1.0.0",
      "description": "description",
      "author": "author",
      "private": true,
      "scripts": {
        "dev": "nuxt",
        "build": "nuxt build",
        "start": "nuxt start",
        "server": "nuxt start",
        "generate": "nuxt generate",
        "lint": "eslint --ext .js,.vue --ignore-path .gitignore .",
        "precommit": "npm run lint"
      },
      "dependencies": {
        "@babel/compat-data": "^7.13.8",
        "axios": "^0.21.1",
        "element-ui": "^2.15.1",
        "js-cookie": "^2.2.1",
        "nuxt": "^2.0.0",
        "vue-awesome-swiper": "^3.1.3",
        "vue-qriously": "^1.1.1"
      },
      "devDependencies": {
        "babel-eslint": "^10.0.1",
        "eslint": "^4.19.1",
        "eslint-friendly-formatter": "^4.0.1",
        "eslint-loader": "^2.1.1",
        "eslint-plugin-vue": "^4.0.0"
      },
      "config": {
        "nuxt": {
          "host": "0.0.0.0",
          "port": "3000"
        }
      }
    }
    
    ```

  - 单点登录需要改的地方

    - ![image-20210609170514878](https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/image-20210609170514878.png)

  - https://www.imooc.com/article/294271



## docker安装

```shell
# https://www.jianshu.com/p/45198f18c996

#!/usr/bin/env bash
docker rm nuxt
docker rmi nuxt

sudo docker build -t nuxt .
#sudo docker rm mynuxt
sudo docker run --name nuxt -it -p 3000:3000 nuxt

FROM node:14.15.1
MAINTAINER He Zhu
ENV NODE_ENV=production
ENV HOST 47.94.174.79
RUN mkdir -p /app
COPY . /app
WORKDIR /app
EXPOSE 3000
#此为cnpm淘宝镜像
#RUN npm config set registry https://registry.npm.taobao.org
RUN npm install
RUN npm i -D webpack-dev-server@2.9.7
RUN npm run build
CMD ["npm", "start"]
```

```shell
# https://zhuanlan.zhihu.com/p/75173720

# Dockerfile
#使用node:8.16-alpine 作为基础进行构建
FROM node:14.15.1 

#创建/app 目录作为部署目录,创建容器实例时,挂载此目录
RUN mkdir -p /app

#更改alpine的安装源指向阿里源
# RUN echo "http://mirrors.aliyun.com/alpine/v3.9/main/" > /etc/apk/repositories

#安装 bash 和 busybox
RUN apk update \
        && apk upgrade \
        && apk add --no-cache bash \
        bash-doc \
        bash-completion \
        && /bin/bash \
        && apk add --no-cache busybox \
        && rm -rf /var/cache/apk/*
#移动工作目录到 /app
WORKDIR /app

#设置node环境变量为production
ENV NODE_ENV=production

#设置容器启动时执行的命令
ENTRYPOINT [ "npm","start" ]


# docker
sudo docker build -t nuxt .

docker run --name nuxt -p 3000:3000 -d --restart=unless-stopped -v /nuxt/prod:/app  nuxt

```

### 安装node

* 淘宝镜像：http://npm.taobao.org/mirrors/node/v14.15.1/
* 阿里云：https://help.aliyun.com/document_detail/50775.html?spm=5176.7841090.6.660.ucjsE4
  * 安全 https://help.aliyun.com/document_detail/25475.html?spm=5176.2020520101.121.2.eKLyed#allowHttp
  * Error: Listen EADDRNOTAVAIL：https://blog.csdn.net/weixin_33895016/article/details/90559461
* 

```shell
#CentOS 安装
#安装 epel-release 依赖：
#yum install epel-release
 
#安装 DNF 包：
#yum install dnf
#sudo dnf upgrade --refresh

# node.js
sudo dnf install -y gcc-c++ make 
curl -sL https://rpm.nodesource.com/setup_14.x | sudo -E bash -
sudo yum install -y nodejs

#digital occean
https://www.digitalocean.com/community/tutorials/how-to-install-node-js-on-centos-8
```

### nacos

```shell
docker run --name nacos -e MODE=standalone -p 8848:8848 -d nacos/nacos-server
docker update 2e50b269b41b --restart=always
```

### mysql

```shell
sudo docker pull mysql
(ENV MYSQL_VERSION=8.0.25-1debian10)
# --name指定容器名字 -v目录挂载 -p指定端口映射  -e设置mysql参数 -d后台运行
sudo docker run -p 3306:3306 --name mysql \
-v /mydata/mysql/log:/var/log/mysql \
-v /mydata/mysql/data:/var/lib/mysql \
-v /mydata/mysql/conf:/etc/mysql \
-e MYSQL_ROOT_PASSWORD=root \
-d mysql

#mysql8
sudo docker run -p 3306:3306 --name mysql \
-e MYSQL_ROOT_PASSWORD=root \
-d mysql
```

```shell
docker exec -it mysql bin/bash

cat /etc/hosts

mysql -uroot -proot
show databases;

exit;

因为有目录映射，所以我们可以直接在镜像外执行
vi /mydata/mysql/conf/my.conf 

[client]
default-character-set=utf8
[mysql]
default-character-set=utf8
[mysqld]
init_connect='SET collation_connection = utf8_unicode_ci'
init_connect='SET NAMES utf8'
character-set-server=utf8
collation-server=utf8_unicode_ci
skip-character-set-client-handshake
skip-name-resolve

保存(注意评论区该配置不对，不是collection而是collation)

docker restart mysql
docker update mysql --restart=always
```

### redis

```shell
# 在虚拟机中
mkdir -p /mydata/redis/conf
touch /mydata/redis/conf/redis.conf

docker pull redis

docker run -p 6379:6379 --name redis \
-v /mydata/redis/data:/data \
-v /mydata/redis/conf/redis.conf:/etc/redis/redis.conf \
-d redis redis-server /etc/redis/redis.conf \
--appendonly yes


# 直接进去redis客户端。
docker exec -it redis redis-cli
```

默认是不持久化的。在配置文件中输入appendonly yes，就可以aof持久化了。修改完docker restart redis，docker -it redis redis-cli

```shell
vim /mydata/redis/conf/redis.conf
# 插入下面内容
appendonly yes
保存

docker restart redis
docker update redis --restart=always

set k1 v1
SHUTDOWN
```

### nginx

```shell
docker pull nginx:1.10
# 随便启动一个nginx实例，只是为了复制出配置，放到docker里作为镜像的统一配置
docker run -p 80:80 --name nginx -d nginx:1.10

cd /mydata/nginx
docker container cp nginx:/etc/nginx .
然后在外部 /mydata/nginx/nginx 有了一堆文件
mv /mydata/nginx/nginx /mydata/nginx/conf
# 停掉nginx
docker stop nginx
docker rm nginx

# 创建新的nginx
docker run -p 80:80 --name nginx \
-v /mydata/nginx/html:/usr/share/nginx/html \
-v /mydata/nginx/logs:/var/log/nginx \
-v /mydata/nginx/conf:/etc/nginx \
-d nginx:1.10

# 注意一下这个路径映射到了/usr/share/nginx/html，我们在nginx配置文件中是写/usr/share/nginx/html，不是写/mydata/nginx/html

docker update nginx --restart=always
```

## 部署后端

* 开启 tomcat 8080端口

* 将 `vue` 打包好的 `dist` 放进 `webapps` 文件夹

* 访问 `http://47.94.174.79:8080/dist` (公网IP地址)

* 部署在 tomcat 或者 nginx 上 https://blog.csdn.net/u013253924/article/details/81028423?utm_medium=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7EBlogCommendFromMachineLearnPai2%7Edefault-1.base&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7EBlogCommendFromMachineLearnPai2%7Edefault-1.base

* `webpack.prod.conf.js`

  * ```js
    // 注意这里是 config.build 不是 config.dev
    templateParameters: {
        BASE_URL: config.build.assetsPublicPath + config.build.assetsSubDirectory
    },
    ```

  * ![image-20210609215435816](https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/image-20210609215435816.png)

* `config/index.js`

  * `assetsPublicPath: './'`
  * ![image-20210609221210379](https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/image-20210609221210379.png)

* `build/utils.js`

  * ```js
    if (options.extract) {
        loaders.push({loader:MiniCssExtractPlugin.loader,
                      options:{
                          publicPath: '../../'
                      }})
    }
    ```

  * ![image-20210609220957027](https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/image-20210609220957027.png)

### tomcat

```shell
docker run -p 8080:8080 --name tomcat8 \
-v /mydata/tomcat/webapps:/usr/local/tomcat/webapps \
-d tomcat:8.0

docker run -p 3000:8080 --name tomcat82 \
-v /mydata/tomcat2/webapps:/usr/local/tomcat/webapps \
-d tomcat:8.0

docker exec -it tomcat8 /bin/bash
```

* http://47.94.174.79:8080/dist

## JDK安装

https://blog.csdn.net/qq_31772441/article/details/80629505
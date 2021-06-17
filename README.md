## 技术栈

后端：Springboot + Mybatis + Vue

前端：Vue

中间件：SpringCloud (Nacos + Feign + Gateway + Hystrix) + SpringSecurity + Redis + OAuth2.0





## 后台管理系统

> 用户名：admin 密码：111111

### 1. Auth Management

![Backend01](https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/Backend01.png)

### 2. Artist Management

![Backend02](https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/Backend02.png)

### 3. Insert new Artist Information

![Backend03](https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/Backend03.png)

### 4. Movie Categories

![Backend04](https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/Backend04.png)

### 5. Movie List

![Backend05](https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/Backend05.png)

### 6. Publish Movie

![Backend06](https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/Backend06.png)

### 7. Statistics

![Backend07](https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/Backend07.png)

## 前台系统

> 用户名：18600146610 密码：111111

### 1. Main Page

![Front1](https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/Front1.png)

### 2. All Movie

![Front2](https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/Front2.png)

### 3. Movie Details

![Front4](https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/Front4.png)

### 4. Artist Detail Page

![Front3](https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/Front3.png)

### 5. Play the Movie

![Front5](https://raw.githubusercontent.com/TWDH/Leetcode-From-Zero/pictures/img/Front5.png)

## 后端测试

`http://localhost:8001/eduservice/course/getCourseList`

`http://localhost:8222/eduservice/course/getCourseList`























## Bug

- **Error creating bean** with name 'bannerAdminController': Unsatisfied dependency expressed through field 'bannerService'; nested exception is org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'crmBannerServiceImpl': Unsatisfied dependency expressed through field 'baseMapper'; nested exception is org.springframework.beans.factory.UnsatisfiedDependencyException: 
  - 将resource设为resource路径
  - 检查.iml文件
  - @MapperScan扫描


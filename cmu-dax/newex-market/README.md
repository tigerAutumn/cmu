newex-dax-market(newex交易平台-行情服务）
=======================================================

# 概述
newex-dax-market:newex交易平台--行情服务

# 开发环境
- [jdk1.8][]
- [maven3][]
- [MySQL5+][]
- [lombok][]
- [spring boot][] 
- [spring mvc][]
- [spring validation][] ([validation入门教程][])
- [spring-security][]
- [springmvc-i18n][]
- [messagesource][]
- [mybatis][]
- [swagger][]
- [logback][]
- [spring aop][]
- [spring boot actuator][]

# 模块说明
- dto: 对外提供的数据传输对象
- core: 核心业务
    - common: 项目公共类
    - exception: 接口返回的数据模型类
    - data：数据访问([mybatis][])类
    - domain：pojo类模块
    - service：业务逻辑类
- rest：基于[spring mvc][] Restful API给前端进行交互
    - config: 项目javaConfig模块
    - controller: mvc控制器
- resources
    - conf: 项目相关配置文件
    - mybatis: mybaits mapper xml文件
    - sql: database初始化表与数据文件
- swagger
    - swagger url: http://localhost:${port}/swagger-ui.html
    
    http://localhost:8107/v1/market/

# 项目构建
利用maven profile把项目分成三个环境配置项目:dev(开发),test(测试),prod(生产).并能过maven的[filter][]机制在构建时自动替换相关的配置项

## 开发环境构建

```shell
mvn clean package -Dmaven.test.skip=true -Pdev
```

## 测试环境构建

```shell
mvn clean package -Dmaven.test.skip=true -Ptest
```

## 生产环境构建

```shell
mvn clean package -Dmaven.test.skip=true -Pprod
```

# 部署 

[jdk1.8]: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
[maven3]: http://maven.apache.org/download.cgi
[lombok]: https://projectlombok.org/download.html
[tomcat8+]: http://tomcat.apache.org/
[MySQL5+]: http://dev.mysql.com/downloads/mysql/
[spring]: http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/
[spring boot]: http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/
[spring mvc]: http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#mvc
[mybatis]: http://www.mybatis.org/mybatis-3/
[swagger]: http://swagger.io/
[logback]: https://logback.qos.ch/manual/
[thymeleaf]: http://www.thymeleaf.org/
[shiro]: http://shiro.apache.org/
[filter]: https://buzheng.org/maven-profile-for-multiple-enviroments.html
[spring validation]: http://docs.spring.io/spring-framework/docs/current/spring-framework-reference/htmlsingle/#validation
[validation入门教程]: http://jinnianshilongnian.iteye.com/blog/1990081
[spring aop]: http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#aop
[spring boot actuator]: http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready
[springmvc-i18n]: http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#mvc-localeresolver
[messagesource]: http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#context-functionality-messagesource
[spring-security]: http://projects.spring.io/spring-security/
[spring-session]: http://projects.spring.io/spring-session/

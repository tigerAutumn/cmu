newex-web(newex交易平台-Web端springboot mvc项目）
=======================================================

# 概述
newex-web:newex交易平台-Web端springboot mvc项目

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

# 包说明

- common: 项目公共类
  - annotation: 自定义注解
  - aop:拦截器
  - config: java config类
  - util: 当前项目需要utils类
- controller: mvc控制器
- model: 视图模型

# 资源说明
- resources
    - conf: 项目相关配置文件
    - mybatis: mybaits mapper xml文件
    - sql: database初始化表与数据文件
- swagger
    - swagger url: http://localhost:${port}/swagger-ui.html

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

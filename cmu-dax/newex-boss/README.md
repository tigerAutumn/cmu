newex-dax-boss(newex交易平台-综合业务管理服务）
=======================================================

# 概述
newex-dax-boss:newex交易平台--综合业务管理服务

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
- client:给其他服务消费者提供api客户端
- dto: 对外提供的数据传输对象层
- service: 核心业务逻辑层
    - admin: 后台用户权限管理模块
    - config: 后台数据配置模块
    - report：后台报表分析模块
- web：基于[spring mvc][] SpringMVC Web层
    - common: web层公共类
    - controller: mvc控制器  
      - common: 公共的controller  
      - inner.v1: 内部调用的controller  
      - outer.v1: 外部调用的controller  
        1.admin:后台用户管理相关controller  
        2.config:后台系统配置管理相关controller  
        3.asset:交易所用户资金管理(充/提币等)相关controller  
        4.extra:交易所辅助服务(活动,cms,banner等)管理相关controller  
        5.futures:交易所合约业务管理相关controller  
        6.report:交易所数据统计分析报表管理相关controller  
        7.risk:交易所风控管理相关controller  
        8.spot:交易所币币交易管理相关controller  
        9.users:交易所用户中心管理相关controller  
        10.workorder:交易所用户工单管理相关controller  
        11.monitor:交易所系统常用数据监控(缓存数据等)管理相关controller  
        12.c2c:交易所法币交易管理相关controller  
        13.integration:交易所集成服务(短信、邮件发等)管理相关controller  
      - HomeController: 后台管理首页controller  
      - LoginController: 后台登录页controller  
    - model:视图模型类  
- resources
    - conf: 项目相关配置文件
    - mybatis: mybaits mapper xml文件
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
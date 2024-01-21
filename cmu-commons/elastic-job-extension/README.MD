# Elastic-Job的SpringBoot 扩展

> 摘要

>由于Elastic-Job对SpringBoot支持不够友好，这里做一点扩展
    

#### POM依赖

```xml
    <dependency>
        <groupId>cc.newex</groupId>
        <artifactId>elastic-job-spring-boot-starter</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </dependency>
```

#### 怎么写一个JOB，如下

```java
    /**
     * @author Liu Hailin
     * @create 2017-08-17 下午9:14
     **/
    
    @Component
    @ElasticJobExtConfig(cron = "0/2 * * * * ?")
    public class Example implements SimpleJob {
        @Override
        public void execute(ShardingContext shardingContext) {
    
            System.out.println("i am a test!");
        }
    }
```
* `@Component` Spring 组件注解，把job注册到容器，方便后面解析使用
* `@ElasticJobExtConfig` 本次扩展组件，目前支持的配置就有必须项，最简单使用方式。

#### SpringBoot配置

入口

```java
    /**
     * @author Liu Hailin
     * @create 2017-08-17 下午9:13
     **/
    
    @SpringBootApplication
    public class BootMain {
        public static void main(String[] args) {
            SpringApplication.run(BootMain.class,args);
        }
    }
```

在application.yml中配置

```xml
    task:
        center:
          serverList: localhost:2181
          namespace: elastic-job-ext
```

OK，以上即可以完成elastic-job的扩展使用。
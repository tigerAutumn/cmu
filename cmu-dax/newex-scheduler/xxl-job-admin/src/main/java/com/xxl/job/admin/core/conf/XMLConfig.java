package com.xxl.job.admin.core.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.xxl"})
@ImportResource(locations={"classpath*:spring/applicationcontext-*.xml"})
public class XMLConfig {


}

package com.atri.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Spring 配置类
 */
@Configuration
@ComponentScan(basePackages = "com.atri")
@MapperScan("com.atri.dao")
public class AppConfig {
}

package com.atri.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "com.atri.view",
        "com.atri.controller",
        "com.atri.scene"})
public class SpringConfiguration {
}

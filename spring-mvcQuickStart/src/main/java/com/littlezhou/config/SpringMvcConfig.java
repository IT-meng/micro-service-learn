package com.littlezhou.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan("com.littlezhou.controller")
 @EnableWebMvc//开启自动转换json数据
public class SpringMvcConfig {
}

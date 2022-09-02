package com.littlezhou.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("com.littlezhou")
@Import({DruidConfig.class,MyBatisConfig.class})
public class SpringConfig {
}

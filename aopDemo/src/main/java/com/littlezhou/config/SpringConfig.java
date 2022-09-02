package com.littlezhou.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("com.littlezhou")
@EnableAspectJAutoProxy
public class SpringConfig {


}

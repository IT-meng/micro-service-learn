package com.littlezhou.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@Import({DruidConfig.class})
@PropertySource("jdbc.properties")
public class SpringConfig {
}

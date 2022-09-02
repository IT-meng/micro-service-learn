package com.littlezhou.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

public class DruidConfig {

    @Value("${jdbc.name}")
    private String userName;
    @Value("${jdbc.password}")
    private String password;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.driver}")
    private String driver;

    @Bean("dataSource")
    public DataSource dataSource(){
        DruidDataSource ds  = new DruidDataSource();
        ds.setUrl(url);
        ds.setDriverClassName(driver);
        ds.setUsername(userName);
        ds.setPassword(password);
        return ds;
    }
}

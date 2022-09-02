package com.littlezhou.configs;

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
    //基础数据类型依赖注入
    @Bean("dataSource")
    DataSource dataSource(){
        DruidDataSource ds = new DruidDataSource();
        ds.setUsername(userName);
        ds.setPassword(password);
        ds.setUrl(url);
        ds.setDriverClassName(driver);
        System.out.println(userName);

        return ds;
    }

}

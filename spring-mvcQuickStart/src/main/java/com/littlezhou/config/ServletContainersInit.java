package com.littlezhou.config;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;


//定义servlet容器启动配置类，在里边加载spring的配置
public class ServletContainersInit extends AbstractDispatcherServletInitializer {
    //加载spring-mvc的容器配置
    @Override
    protected WebApplicationContext createServletApplicationContext() {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(SpringMvcConfig.class);
        return ctx;
    }

    //配置哪些请求交给spring-mvc处理
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    //加载spring的容器配置
    @Override
    protected WebApplicationContext createRootApplicationContext() {
        return null;
    }
}

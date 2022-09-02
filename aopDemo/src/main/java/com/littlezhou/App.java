package com.littlezhou;

import com.littlezhou.config.SpringConfig;
import com.littlezhou.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx  = new AnnotationConfigApplicationContext(SpringConfig.class);
        UserService service = ctx.getBean(UserService.class);
        service.save();
    }
}

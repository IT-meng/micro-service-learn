package com.littlezhou.service;

import com.littlezhou.dao.UserDao;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserServiceTest {

    @Test
    public void testPrint(){
        //获取Ioc容器
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationcontext.xml");
//        UserDao userDao = (UserDao) ctx.getBean("userDao");
//        userDao.print();
        UserService userService = (UserService) ctx.getBean("userService");
        userService.print();
    }
}

package com.littlezhou.service;

import com.littlezhou.dao.BookDao;
import com.littlezhou.dao.OrderDao;
import com.littlezhou.dao.UserDao;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestNewBeanWays {
    @Test
    public void testStaticFactoryWay(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationcontext.xml");
        OrderDao orderDao1 = (OrderDao) ctx.getBean("orderDao");
        OrderDao orderDao2 = (OrderDao) ctx.getBean("orderDao");
        System.out.println(orderDao1);
        System.out.println(orderDao2);
        //静态工厂默认也是单例
    }

    @Test
    public void testInstanceFactory(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationcontext.xml");
        UserDao userDao1 = (UserDao) ctx.getBean("userDao");
        UserDao userDao2 = (UserDao) ctx.getBean("userDao");
        userDao1.print();
        System.out.println(userDao1);
        System.out.println(userDao2);
        //默认单例

    }

    @Test
    public void testForthWay(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationcontext.xml");
        BookDao bookDao1 = (BookDao) ctx.getBean("bookDao");
        BookDao bookDao2 = (BookDao) ctx.getBean("bookDao");
        bookDao1.save();
        System.out.println(bookDao1);
        System.out.println(bookDao2);
    }

}

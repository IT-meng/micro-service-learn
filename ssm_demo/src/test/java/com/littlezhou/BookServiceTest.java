package com.littlezhou;

import com.littlezhou.config.SpringConfig;
import com.littlezhou.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringJUnitConfig(classes = SpringConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class BookServiceTest {
    @Autowired
    private BookService service;

    @Test
    public void getByIdTest(){
        System.out.println(service.getById(2));
    }

    @Test
    public void getAllTest(){
        System.out.println(service.getAll());
    }

}

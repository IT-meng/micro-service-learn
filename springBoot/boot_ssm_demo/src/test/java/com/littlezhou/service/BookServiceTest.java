package com.littlezhou.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
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

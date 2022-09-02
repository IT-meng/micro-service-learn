package com.littlezhou.service;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FileServiceTest {
    @Test
    public void testFileService(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationcontext.xml");
        FileService  fileService = (FileService) ctx.getBean("fileService");
        fileService.save();
    }
}

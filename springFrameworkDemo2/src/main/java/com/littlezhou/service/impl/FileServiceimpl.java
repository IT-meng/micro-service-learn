package com.littlezhou.service.impl;

import com.littlezhou.dao.BookDao;
import com.littlezhou.service.FileService;

public class FileServiceimpl implements FileService {
    private BookDao bookDao;
    private int num;
    private String name;
    @Override
    public void save() {
        System.out.println(bookDao);
        System.out.println(num);
        System.out.println(name);
        bookDao.save();
    }

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setName(String name) {
        this.name = name;
    }

}

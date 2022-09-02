package com.littlezhou.dao.impl;

import com.littlezhou.dao.BookDao;

public class BookDaoimpl implements BookDao {
    @Override
    public void save() {
        System.out.println("BookDao save...");
    }
}

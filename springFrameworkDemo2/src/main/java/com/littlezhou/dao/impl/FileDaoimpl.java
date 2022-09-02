package com.littlezhou.dao.impl;

import com.littlezhou.dao.FileDao;

public class FileDaoimpl implements FileDao {

    @Override
    public void save() {
        System.out.println("fileDao save");
    }
}

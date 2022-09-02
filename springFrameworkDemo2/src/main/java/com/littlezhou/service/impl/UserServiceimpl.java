package com.littlezhou.service.impl;

import com.littlezhou.dao.UserDao;
import com.littlezhou.service.UserService;

public class UserServiceimpl implements UserService {
    private UserDao userDao;
    @Override
    public void print() {
        userDao.print();
        System.out.println("service print .. ");
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}

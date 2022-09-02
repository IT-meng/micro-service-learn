package com.littlezhou.service.impl;

import com.littlezhou.dao.UserDao;
import com.littlezhou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller
public class UserServiceImpl implements UserService {
@Autowired
 private UserDao userDao;
    @Override
    public void save() {
        userDao.save();
        System.out.println("service save ...");
    }
}

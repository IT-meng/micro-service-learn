package com.littlezhou.dao.impl;

import com.littlezhou.dao.UserDao;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {


    @Override
    public void save() {
        System.out.println("dao save ...");
    }
}

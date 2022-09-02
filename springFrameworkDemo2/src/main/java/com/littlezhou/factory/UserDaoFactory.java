package com.littlezhou.factory;

import com.littlezhou.dao.UserDao;
import com.littlezhou.dao.impl.UserDaoimpl;

public class UserDaoFactory {

    public UserDao getUserDao(){
        return new UserDaoimpl();
    }
}

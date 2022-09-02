package com.littlezhou.factory;

import com.littlezhou.dao.OrderDao;
import com.littlezhou.dao.impl.OrderDaoimpl;

public class OrderDaoFactory {
    public static OrderDao getOrderDao(){
        return new OrderDaoimpl();
    }
}

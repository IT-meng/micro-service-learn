package com.littlezhou.factory;

import com.littlezhou.dao.BookDao;
import com.littlezhou.dao.impl.BookDaoimpl;
import org.springframework.beans.factory.FactoryBean;

public class BookDaoFactory implements FactoryBean<BookDao> {
   //创建对象的方法
    @Override
    public BookDao getObject() throws Exception {
        return new BookDaoimpl();
    }

    //创建什么对象
    @Override
    public Class<?> getObjectType() {
        return BookDao.class;
    }



}

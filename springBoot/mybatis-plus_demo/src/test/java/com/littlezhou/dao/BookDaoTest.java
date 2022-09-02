package com.littlezhou.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.littlezhou.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class BookDaoTest {
    @Autowired
    BookDao bookDao;

    @Test
    public void getAllTest() {
        List<Book> books = bookDao.selectList(null);
        System.out.println(books);
    }

    @Test
    public void getByIdTest() {
        Book book = bookDao.selectById(12);
        System.out.println(book);
    }

    //分页查询(需要开启Mp的拦截器)
    @Test
    public void selectPageTest(){
        IPage<Book> page = new Page<>(2,2);
        bookDao.selectPage(page, null);
        System.out.println(page.getRecords());
        System.out.println("current: "+page.getCurrent());
        System.out.println("pages: "+page.getPages());
        System.out.println("total: "+page.getTotal());

    }

    //条件查询
    @Test
    public void QueryWithCondition(){
        QueryWrapper wrapper = new QueryWrapper();

        //设置查询条件
        //            wrapper.lt("age",10);

            //查询投影
            //设置查询哪些字段
//            wrapper.select("id","type","name");
//            wrapper.select("count(*) as num");
//            bookDao.selectMaps(wrapper);


    }

}

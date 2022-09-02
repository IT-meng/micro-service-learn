package com.littlezhou.service;

import com.littlezhou.domain.Book;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BookService {

    boolean save(Book book);

    boolean update(Book book);

    Book getById(Integer id);

    List<Book> getAll();

    boolean deleteById(Integer id);
}

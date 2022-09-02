package com.littlezhou.service;

import com.littlezhou.domain.Book;

import java.util.List;


public interface BookService {

    boolean save(Book book);

    boolean update(Book book);

    Book getById(Integer id);

    List<Book> getAll();

    boolean deleteById(Integer id);
}

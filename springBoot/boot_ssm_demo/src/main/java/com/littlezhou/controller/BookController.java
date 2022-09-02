package com.littlezhou.controller;

import com.littlezhou.domain.Book;
import com.littlezhou.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService service;

    @PostMapping
    public Result save(@RequestBody Book book) {

        boolean flag = service.save(book);
        return new Result(flag?Code.SAVE_OK:Code.SAVE_ERR,flag);
    }

    @PutMapping
    public Result update(@RequestBody Book book) {
        boolean flag = service.update(book);
        return new Result(flag?Code.UPDATE_OK:Code.UPDATE_ERR,flag);
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {

        Book book = service.getById(id);
        if(book!=null){
            return new Result(Code.GET_OK,book);
        }else{
            return new Result(Code.GET_ERR,null);
        }
    }
    @GetMapping
    public Result getAll() {
        List<Book> bookList = service.getAll();
        if(bookList==null){
            return new Result(Code.GET_ERR,null);
        }else{
            return new Result(Code.GET_OK,bookList);
        }
    }

    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Integer id){
        boolean flag = service.deleteById(id);
        return new Result(flag?Code.DELETE_OK:Code.DELETE_ERR,flag);
    }


}

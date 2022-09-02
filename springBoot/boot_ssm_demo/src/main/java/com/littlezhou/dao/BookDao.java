package com.littlezhou.dao;

import com.littlezhou.domain.Book;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper //TODO
public interface BookDao {

    @Insert("insert into tbl_book values(null,#{type},#{name},#{description})")
     int save(Book book);

    @Update("update tbl_book set type =#{type}, name =#{name}, description =#{description} where id =#{id}")
    int update(Book book);

    @Select("select * from tbl_book where id = #{id}")
    Book getById(Integer id);

    @Select("select * from tbl_book")
    List<Book> getAll();

    @Delete("delete from tbl_book where id = #{id}")
    int deleteById(Integer id);
}

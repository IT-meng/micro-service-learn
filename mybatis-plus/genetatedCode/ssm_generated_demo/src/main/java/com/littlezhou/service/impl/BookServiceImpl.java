package com.littlezhou.service.impl;

import com.littlezhou.domain.Book;
import com.littlezhou.dao.BookDao;
import com.littlezhou.service.IBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 小周
 * @since 2022-06-22
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookDao, Book> implements IBookService {

}

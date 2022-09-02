package com.littlezhou.dao;

import com.littlezhou.domain.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Select("select * from tab_user")
    User getUser();

}

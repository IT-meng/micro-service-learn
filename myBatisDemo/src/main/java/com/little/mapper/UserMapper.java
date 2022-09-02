package com.little.mapper;


import com.little.domain.User;

import java.util.List;

public interface UserMapper {
    List<User> selectAll();
}

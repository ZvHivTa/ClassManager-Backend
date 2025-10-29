package com.zht.newclassmanager.mapper;

import com.zht.newclassmanager.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User selectUser(User user);
}

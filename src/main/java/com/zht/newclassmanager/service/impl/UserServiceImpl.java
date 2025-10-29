package com.zht.newclassmanager.service.impl;

import com.zht.newclassmanager.mapper.UserMapper;
import com.zht.newclassmanager.pojo.User;
import com.zht.newclassmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//Service注解，使spring框架在注入时得知此类是业务逻辑层的类
@Transactional
@Service
public class UserServiceImpl implements UserService {
    //Autowired注解自动寻找同类型同名的bean对象注入，优先级是同类型同名最优先、同类型不同名次之
    @Autowired
    private UserMapper userMapper;
    @Override
    public User login(User user) {
        return this.userMapper.selectUser(user);
    }
}

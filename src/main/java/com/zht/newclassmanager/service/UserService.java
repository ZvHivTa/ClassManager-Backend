package com.zht.newclassmanager.service;

import com.zht.newclassmanager.pojo.DTO.UserLoginDTO;
import com.zht.newclassmanager.pojo.DTO.UserPasswordChangeDTO;
import com.zht.newclassmanager.pojo.User;
import com.zht.newclassmanager.pojo.VO.UserLoginVO;


public interface UserService {
    UserLoginVO login(UserLoginDTO userLoginDTO);

    void pwdChange(UserPasswordChangeDTO userPasswordChangeDTO);


}

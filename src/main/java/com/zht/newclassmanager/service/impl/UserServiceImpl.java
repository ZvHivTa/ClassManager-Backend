package com.zht.newclassmanager.service.impl;

import com.zht.newclassmanager.constant.MessageConstant;
import com.zht.newclassmanager.exception.AccountNotFoundException;
import com.zht.newclassmanager.exception.PasswordErrorException;
import com.zht.newclassmanager.mapper.ManagerMapper;
import com.zht.newclassmanager.mapper.StudentMapper;
import com.zht.newclassmanager.mapper.UserMapper;
import com.zht.newclassmanager.pojo.DTO.UserLoginDTO;
import com.zht.newclassmanager.pojo.DTO.UserPasswordChangeDTO;
import com.zht.newclassmanager.pojo.Manager;
import com.zht.newclassmanager.pojo.Student;
import com.zht.newclassmanager.pojo.User;
import com.zht.newclassmanager.properties.JwtProperties;
import com.zht.newclassmanager.service.UserService;
import com.zht.newclassmanager.utils.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zht.newclassmanager.pojo.VO.UserLoginVO;
import com.zht.newclassmanager.enumration.Roletype;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private ManagerMapper managerMapper;
    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserLoginVO login(UserLoginDTO userLoginDTO) {
        // 1. 查询用户基础表
        User user = new User();
        BeanUtils.copyProperties(userLoginDTO,user);
        user = userMapper.selectUser(user);

        // 2. 校验账号是否存在
        if (user == null) {
            throw new AccountNotFoundException();
        }

        if (!passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
            // 密码不匹配
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }
        //到这说明已经登录成功了，装填角色
        Roletype role = Roletype.STUDENT; // 默认

        if (user.getId().toString().length() == 6) {
            role = Roletype.ADMIN;
        }
        user.setRoletype(role);
        // 4. 生成 Token
        Map<String, Object> claims = new HashMap<>();
        claims.put("account", user.getId());
        claims.put("role", user.getRoletype()); // 统一使用 role 或 type

        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);

        // 5. 组装 VO
        UserLoginVO vo = new UserLoginVO();
        vo.setToken(token);
        vo.setRoletype(user.getRoletype()); // 假设 VO 有 role 字段

        // 6. 根据角色补充详细信息
        if (user.getRoletype() == Roletype.ADMIN) {
            Manager manager = managerMapper.selectById(user.getId());
            BeanUtils.copyProperties(manager, vo); // 假设 VO 兼容 Manager 字段
        } else if (user.getRoletype() == Roletype.STUDENT) {
            Student student = studentMapper.selectById(user.getId());
            BeanUtils.copyProperties(student, vo);
        }
        return vo;
    }

    @Override
    public void pwdChange(UserPasswordChangeDTO userPasswordChangeDTO) {
        User user = new User();
        user.setId(userPasswordChangeDTO.getId());
        user.setPassword(passwordEncoder.encode(userPasswordChangeDTO.getOldPassword()));
        User user1 = userMapper.selectUser(user);

        if (user1 == null) {
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        String newPassword = userPasswordChangeDTO.getNewPassword();

        if (newPassword == null || newPassword.length() < 8 || newPassword.length() > 100) {
            throw new PasswordErrorException("新密码长度必须在 8-100 位之间");
        }

        String encodedNewPassword = passwordEncoder.encode(newPassword);


        User userToUpdate = new User();
        userToUpdate.setId(userToUpdate.getId());
        userToUpdate.setPassword(encodedNewPassword);

        userMapper.updatePassword(userToUpdate);
    }
}

package com.zht.newclassmanager.contoller;


import com.zht.newclassmanager.enumration.Roletype;
import com.zht.newclassmanager.pojo.DTO.UserLoginDTO;
import com.zht.newclassmanager.pojo.Manager;
import com.zht.newclassmanager.pojo.Student;
import com.zht.newclassmanager.pojo.User;
import com.zht.newclassmanager.pojo.VO.UserLoginVO;
import com.zht.newclassmanager.properties.JwtProperties;
import com.zht.newclassmanager.result.Result;
import com.zht.newclassmanager.service.ManagerService;
import com.zht.newclassmanager.service.StudentService;
import com.zht.newclassmanager.service.UserService;
import com.zht.newclassmanager.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RestController
@RequestMapping("/api")
@Tag(name = "共通")
public class CommonController {
    @Autowired
    UserService userService;

    @Autowired
    JwtProperties jwtProperties;

    @Autowired
    StudentService studentService;

    @Autowired
    ManagerService managerService;

    @PostMapping(value="/login")
    @Operation(description = "登录")
    public Result<UserLoginVO> login(UserLoginDTO UserLoginDTO){
        User user = new User();
        BeanUtils.copyProperties(UserLoginDTO,user);

        user = userService.login(user);

        HashMap<String, Object> claims = new HashMap<>();
        claims.put("account", user.getAccount());
        claims.put("type", user.getRoletype());

        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);

        UserLoginVO result = UserLoginVO.builder()
                .token(token)
                .build();

        if(user.getRoletype() == Roletype.admin){
            Manager manager = managerService.showManagerInfo(user.getAccount());
            BeanUtils.copyProperties(manager,result);
        }else{
            Student student = studentService.showStudentInfo(user.getAccount());
            BeanUtils.copyProperties(student,result);
        }
        return Result.success(result);
    }
}

package com.zht.newclassmanager.contoller;


import com.zht.newclassmanager.pojo.DTO.UserLoginDTO;
import com.zht.newclassmanager.pojo.User;
import com.zht.newclassmanager.pojo.VO.UserLoginVO;
import com.zht.newclassmanager.properties.JwtProperties;
import com.zht.newclassmanager.result.Result;
import com.zht.newclassmanager.service.UserService;
import com.zht.newclassmanager.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RestController
@RequestMapping
@Tag(name = "共通")
public class CommonController {
    @Autowired
    UserService userService;

    @Autowired
    JwtProperties jwtProperties;

    @PostMapping(value="/login")
    @Operation(description = "登录")
    public Result<UserLoginVO> login(UserLoginDTO UserLoginDTO){
        User user = new User();
        BeanUtils.copyProperties(UserLoginDTO,user);

        user = userService.login(user);
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("account", user.getAccount());
        claims.put("type", user.getType());

        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);


        UserLoginVO result = UserLoginVO.builder()
                .token(token)
                .id(user.getAccount())
                .build();
        return Result.success(result);
    }
}

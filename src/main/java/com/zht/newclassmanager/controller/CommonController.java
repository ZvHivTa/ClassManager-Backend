package com.zht.newclassmanager.controller;

import com.zht.newclassmanager.constant.MessageConstant;
import com.zht.newclassmanager.context.BaseContext;
import com.zht.newclassmanager.pojo.DTO.UserLoginDTO;
import com.zht.newclassmanager.pojo.DTO.UserPasswordChangeDTO;
import com.zht.newclassmanager.pojo.VO.UserLoginVO;
import com.zht.newclassmanager.result.Result;
import com.zht.newclassmanager.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "共通接口")
@Slf4j
public class CommonController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param userLoginDTO 登录请求参数
     * @return 登录结果，包含Token和用户信息
     */
    @PostMapping("/login")
    @Operation(summary = "登录", description = "支持学生和管理员登录")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户登录: {}", userLoginDTO.getId());
        UserLoginVO userLoginVO = userService.login(userLoginDTO);
        return Result.success(userLoginVO);
    }

    @PostMapping("/pwdChange")
    @Operation(summary = "修改密码", description = "支持学生和管理员的密码修改")
    public Result pwdChange(@RequestBody UserPasswordChangeDTO userPasswordChangeDTO) {
        Integer currentId = BaseContext.getCurrentId();
        userPasswordChangeDTO.setId(currentId);
        log.info("用户修改密码: {}", currentId);

        userService.pwdChange(userPasswordChangeDTO);
        return Result.success();
    }
}
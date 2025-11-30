package com.zht.newclassmanager.controller;

import com.zht.newclassmanager.constant.MessageConstant;
import com.zht.newclassmanager.pojo.DTO.UserLoginDTO;
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

        // 核心逻辑下沉到 Service 层
        // Service 层负责：
        // 1. 校验账号密码
        // 2. 生成 JWT Token
        // 3. 根据角色查询详细信息（Student 或 Manager）
        // 4. 组装并返回 UserLoginVO
        UserLoginVO userLoginVO = userService.login(userLoginDTO);

        // 如果登录失败（例如账号不存在或密码错误），Service 层应抛出异常
        // 全局异常处理器会捕获异常并返回 Result.error(...)
        // 因此这里只需处理成功的情况

        return Result.success(userLoginVO);
    }
}
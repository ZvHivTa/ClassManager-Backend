package com.zht.newclassmanager.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.zht.newclassmanager.context.BaseContext;
import com.zht.newclassmanager.properties.JwtProperties;
import com.zht.newclassmanager.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Collections;

@Component
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. 获取 Authorization 头
        String token = request.getHeader("Authorization");

        // 2. 校验 Token 格式
        // 如果没有 Token 或格式不对，直接放行 (此时 SecurityContext 为空，后续的安全拦截器会处理 401)
        if (!StringUtils.hasText(token) || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. 去掉 Bearer 前缀
        token = token.substring(7);

        try {
            // 4. 解析 Token
            // 假设你只有一套密钥。如果区分 admin/student 端，需根据 request URI 选择不同的 SecretKey
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);

            // 5. 获取用户标识 (account/id)
            Object accountObj = claims.get("account"); // 确保生成 Token 时 Claims 的 key 也是 "account"

            if (accountObj != null) {
                Integer userId = Integer.valueOf(accountObj.toString());

                // [关键动作 A]：告诉 Spring Security 用户已登录
                // 第三个参数 authorities 目前为空，后续做 RBAC 权限控制时需填充角色列表
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userId, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // [关键动作 B]：存入自定义 ThreadLocal (方便 Controller/Service 层直接获取 ID)
                BaseContext.setCurrentId(userId);

                log.debug("用户[{}]认证通过", userId);
            }
        } catch (Exception e) {
            // 解析失败（如 Token 过期、签名错误），打印日志但不抛出异常
            // 保持 SecurityContext 为空，请求继续向下走，最终会被 Spring Security 的 EntryPoint 拦截并返回 401
            log.warn("Token解析失败或无效: {}", e.getMessage());
        }

        // 6. 放行请求
        try {
            filterChain.doFilter(request, response);
        } finally {
            // [至关重要] 请求结束清理 ThreadLocal，防止内存泄漏和线程复用导致的数据串号
            BaseContext.removeCurrentId();
        }
    }
}
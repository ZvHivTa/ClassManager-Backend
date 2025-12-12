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
        String token = request.getHeader("Authorization");// 前端发的是 Authorization
        System.out.print(token);
        log.info("解析到token: {}", token);
        // 2. 去掉 Bearer 前缀
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // 3. 解析 Token (假设你只有一套密钥，如果分 admin/student 需要在这里判断)
            // 简单起见，这里假设用统一的 UserSecretKey，或者你需要根据路径判断用哪个 Key
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);

            // 4. 获取数据
            // 注意：这里要和你生成 Token 时的 Claims Key 一致 ("account", "id" 等)
            // 假设生成时：claims.put("account", id);
            Object accountObj = claims.get("account");

            if (accountObj != null) {
                Integer userId = Integer.valueOf(accountObj.toString());

                // [关键修改]：同时设置 SecurityContext 和 BaseContext

                // A. 告诉 Spring Security (用于鉴权)
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userId, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // B. 告诉 BaseContext (用于 Controller/Service 方便获取 ID)
                BaseContext.setCurrentId(userId);
            }
        } catch (Exception e) {
            log.error("Token解析失败: {}", e.getMessage());
            // 解析失败不抛异常，SecurityContext 为空，后续会自动报 401
        }

        // 5. 放行
        try {
            filterChain.doFilter(request, response);
        } finally {
            // [关键] 请求结束清理 ThreadLocal，防止内存泄漏
            BaseContext.removeCurrentId();
        }
    }
}

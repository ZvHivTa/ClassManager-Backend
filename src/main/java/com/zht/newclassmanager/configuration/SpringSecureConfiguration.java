package com.zht.newclassmanager.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecureConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 1. 关闭 CSRF (如果是前后端分离)
        http.csrf(csrf -> csrf.disable())

                // 2. 授权配置
                .authorizeHttpRequests(auth -> auth
                        // 放行登录接口和静态资源
                        .requestMatchers("/api/login", "/api/doc.html", "/webjars/**", "/v3/api-docs/**").permitAll()
                        // 其他接口需要认证
                        .anyRequest().authenticated()
                )

                // 3. 禁用 HTTP Basic
                // 这就是防止弹出那个浏览器框的核心配置
                .httpBasic(httpBasic -> httpBasic.disable())

                // 4. 异常处理 (可选)
                // 当未登录访问受保护资源时，返回 401 JSON 而不是跳转或弹窗
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setContentType("application/json;charset=UTF-8");
                            response.setStatus(401);
                            response.getWriter().write("{\"code\":0, \"msg\":\"未登录或Token失效\", \"data\":null, \"success\":false}");
                        })
                );

        return http.build();
    }
}

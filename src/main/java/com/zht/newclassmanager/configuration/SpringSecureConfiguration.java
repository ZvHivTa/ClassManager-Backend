package com.zht.newclassmanager.configuration;

import com.zht.newclassmanager.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SpringSecureConfiguration {

    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. 关闭 CSRF (前后端分离项目不需要)
                .csrf(csrf -> csrf.disable())

                // 【新增】2. 开启跨域支持 (Spring Security 会自动寻找 CorsConfigurationSource Bean)
                .cors(cors -> {})

                // 【新增】3. 禁用 Session (JWT 核心配置)
                // 确保 Spring Security 不会生成 JSESSIONID，每次请求都必须带 Token
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 4. 授权配置
                .authorizeHttpRequests(auth -> auth
                        // 静态资源和文档放行
                        .requestMatchers(
                                "/api/login",
                                "/doc.html",
                                "/webjars/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/favicon.ico"
                        ).permitAll()
                        // 跨域预检请求放行 (虽然 .cors() 也就是干这个，但有时候需要显式放行 OPTIONS)
                        // .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // 其他接口需要认证
                        .anyRequest().authenticated()
                )

                // 5. 禁用 HTTP Basic (防止浏览器弹窗)
                .httpBasic(httpBasic -> httpBasic.disable())

                // 6. 异常处理
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setContentType("application/json;charset=UTF-8");
                            response.setStatus(401);
                            response.getWriter().write("{\"code\":401, \"msg\":\"未登录或Token失效\", \"data\":null, \"success\":false}");
                        })
                        // 可选：处理 403 权限不足
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setContentType("application/json;charset=UTF-8");
                            response.setStatus(403);
                            response.getWriter().write("{\"code\":403, \"msg\":\"权限不足\", \"data\":null, \"success\":false}");
                        })
                )

                // 7. 添加 JWT 过滤器
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
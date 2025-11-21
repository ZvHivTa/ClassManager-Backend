package com.zht.newclassmanager.configuration;

import com.zht.newclassmanager.interceptor.JwtTokenManagerInterceptor;
import com.zht.newclassmanager.interceptor.JwtTokenStudentInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer; // 关键点：引入这个接口

/**
 * 配置类，注册web层相关组件
 */
@Configuration
@Slf4j
// 1. 这里的 extends WebMvcConfigurationSupport 改为 implements WebMvcConfigurer
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private JwtTokenManagerInterceptor jwtTokenManagerInterceptor;

    @Autowired
    private JwtTokenStudentInterceptor jwtTokenStudentInterceptor;

    /**
     * 注册自定义拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册自定义拦截器...");

        // 你的拦截路径很规范，没有拦截 /**，所以通常不需要额外排除 swagger
        // 但为了保险起见，建议显式排除

        registry.addInterceptor(jwtTokenManagerInterceptor)
                .addPathPatterns("/api/manager/**")
                .excludePathPatterns("/swagger-ui/**", "/v3/api-docs/**"); // 保险起见

        registry.addInterceptor(jwtTokenStudentInterceptor)
                .addPathPatterns("/api/student/**")
                .excludePathPatterns("/swagger-ui/**", "/v3/api-docs/**"); // 保险起见
    }

    // 2. 这里的 addResourceHandlers 方法可以直接删除
    // 因为实现 WebMvcConfigurer 后，Spring Boot 会自动把 swagger-ui 的资源映射好
}
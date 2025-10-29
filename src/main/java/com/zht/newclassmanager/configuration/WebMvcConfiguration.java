package com.zht.newclassmanager.configuration;


import com.zht.newclassmanager.interceptor.JwtTokenManagerInterceptor;
import com.zht.newclassmanager.interceptor.JwtTokenStudentInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * 配置类，注册web层相关组件
 */
@Configuration
@Slf4j
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    @Autowired
    private JwtTokenManagerInterceptor jwtTokenManagerInterceptor;

    @Autowired
    private JwtTokenStudentInterceptor jwtTokenStudentInterceptor;

    /**
     * 注册自定义拦截器
     *
     * @param registry
     */
    protected void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册自定义拦截器...");
        registry.addInterceptor(jwtTokenManagerInterceptor)
                .addPathPatterns("/manager/**");

        registry.addInterceptor(jwtTokenStudentInterceptor)
                .addPathPatterns("/student/**");
    }

    /**
     * 设置静态资源映射
     * @param registry
     */
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}

package com.zht.newclassmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement //开启注解方式的事务管理
public class NewClassManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewClassManagerApplication.class, args);
    }

}

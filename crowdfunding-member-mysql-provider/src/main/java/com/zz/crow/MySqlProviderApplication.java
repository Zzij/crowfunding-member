package com.zz.crow;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zz.crow.mapper")
public class MySqlProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(MySqlProviderApplication.class, args);
    }
}

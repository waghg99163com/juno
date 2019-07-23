package com.juno;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories
@MapperScan("com.juno.mapper")
public class JunoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JunoApplication.class, args);
    }

}

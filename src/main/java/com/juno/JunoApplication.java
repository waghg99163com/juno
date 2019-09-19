package com.juno;

import com.juno.datasource.DBConfig1;
import com.juno.datasource.DBConfig2;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//不加该注解的话会报没有DBConfig1这个bean的错
@EnableConfigurationProperties(value = {DBConfig1.class,DBConfig2.class})
//@EnableJpaRepositories
@MapperScan("com.juno.mapper")
@EnableScheduling
public class JunoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JunoApplication.class, args);
    }

}

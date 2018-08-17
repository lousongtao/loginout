package com.zhang.task;

import org.apache.log4j.PropertyConfigurator;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@MapperScan("com.snaoad.flow.dao")
@EnableTransactionManagement
@EnableScheduling
public class TaskApplication {
    static {
        PropertyConfigurator.configureAndWatch("config/log4j.properties");
    }

    public static void main(String[] args) {
        SpringApplication.run(TaskApplication.class, args);
    }
}
package com.zhang.task;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ImportResource(locations = {"classpath:applicationContext-dubbo-task.xml"})
public class TaskApplication {
    static {
        PropertyConfigurator.configureAndWatch("config/log4j.properties");
    }
    public static void main(String[] args) {
        SpringApplication.run(TaskApplication.class, args);
    }
}
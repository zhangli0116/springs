package com.test.mspringboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.io.IOException;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableConfigurationProperties
@MapperScan("com.test.mspringboot.mapper") //扫描mapper包,如果mapper没有使用@Mapper注解
public class MSpringBootApplication {
	public static void main(String[] args) {
		SpringApplication.run(MSpringBootApplication.class, args);
	}
}

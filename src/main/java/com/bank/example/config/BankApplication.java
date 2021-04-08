package com.bank.example.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.activation.DataSource;

@ComponentScan("com.bank.example")
@EntityScan("com.bank.example.model")
@EnableTransactionManagement
@SpringBootApplication
public class BankApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

//    @Bean
//    @Autowired
//    public DataSource dataSource(PGSimpleDataSource dataSource) {
//        return DataSourceBuilder
//                .create()
//                .username("")
//                .password("")
//                .url("")
//                .driverClassName("")
//                .build();
//    }
}

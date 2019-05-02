package com.toy.springboottoy;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootToyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootToyApplication.class, args);
    }

    // todo : move config package
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}

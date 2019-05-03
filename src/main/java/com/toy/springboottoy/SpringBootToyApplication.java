package com.toy.springboottoy;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@SpringBootApplication
@EntityScan(
        basePackageClasses = {Jsr310JpaConverters.class},
        basePackages = {"com"})
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

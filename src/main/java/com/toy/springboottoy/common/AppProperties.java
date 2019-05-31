package com.toy.springboottoy.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotEmpty;
import java.util.Map;

@Configuration
@ConfigurationProperties("my-app")
@Getter @Setter
public class AppProperties {

    @Value("#{fixture.userId}")
    private String userId;
    @Value("#{fixture.userPassword}")
    private String userPassword;

}

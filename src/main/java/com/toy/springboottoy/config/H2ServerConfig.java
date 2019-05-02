package com.toy.springboottoy.config;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class H2ServerConfig {

    @Bean
    public Server h2TcpServer() throws Exception {
        return Server.createTcpServer().start();
    }
}

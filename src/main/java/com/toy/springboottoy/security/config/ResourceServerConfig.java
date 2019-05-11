package com.toy.springboottoy.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private DefaultTokenServices tokenServices;

//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.
//                csrf().disable()
//                .authorizeRequests()
//                .antMatchers( "/api/users/**", "/api/users").authenticated()
//                .antMatchers(HttpMethod.GET, "/configuration/security").permitAll()
//                .anyRequest().authenticated();
//    }

    @Override
    public void configure(ResourceServerSecurityConfigurer config) {
        config.tokenServices(tokenServices);
    }

}


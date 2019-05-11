package com.toy.springboottoy.security.component;


import com.toy.springboottoy.security.domain.Member;
import com.toy.springboottoy.security.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class InitComponent implements ApplicationRunner {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void run(ApplicationArguments args) {
//        Member user = new Member("wonchul", passwordEncoder.encode("1234"),"USER");
//        repository.save(user);

//        Member admin = new Member("naeun", passwordEncoder.encode("1234"), "ADMIN");
//        repository.save(admin);
    }
}
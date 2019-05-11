package com.toy.springboottoy.security;


import com.toy.springboottoy.security.domain.Member;
import com.toy.springboottoy.security.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetails implements UserDetailsService {

    @Autowired
    private MemberRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Member> findAll() {
        return userRepository.findAll();
    }

    public Member save(Member member) {
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        return userRepository.save(member);
    }

    @PostConstruct
    public void init(){
        Member juyoung = userRepository.findByUsername("juyoung");
        if(juyoung == null){
            Member account = new Member();
            account.setUsername("juyoung");
            account.setPassword("pass");
            Member newAccount = this.save(account);
            System.out.println(newAccount);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member account = userRepository.findByUsername(username);
        return new org.springframework.security.core.userdetails.User(account.getUsername(), account.getPassword(),getAuthorities());
    }

    private Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }
}
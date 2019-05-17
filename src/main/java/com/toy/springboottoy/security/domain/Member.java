package com.toy.springboottoy.security.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
@RequiredArgsConstructor
@ToString
public class Member {
    @Id @GeneratedValue
    private Long id;
    private String username;
    private String password;

}
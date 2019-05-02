package com.toy.springboottoy.account.domain;

import lombok.*;

import javax.persistence.*;

//todo : ? setter : use modelMapper, getter 분리 방법 : responseDto id 제외 중복
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String userName;
    private String password;
    @Column(unique = true)
    private String email;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    private boolean mailYn;
    private boolean state;

    @Builder
    public Account(String userName,
                   String password,
                   String email,
                   Role role,
                   boolean mailYn,
                   boolean state) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.role = role;
        this.mailYn = mailYn;
        this.state = state;
    }
}
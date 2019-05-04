package com.toy.springboottoy.account.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;

@Entity
@Getter
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String password;
    @Email
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;
    private boolean mailYn;
    private boolean state;
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "update_at", updatable = false)
    private LocalDateTime updateAt;

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
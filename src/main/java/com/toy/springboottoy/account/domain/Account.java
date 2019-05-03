package com.toy.springboottoy.account.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    @CreatedDate
    @Column(name="created_at",updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name="update_at", updatable = false)
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
package com.toy.springboottoy.account.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;
    @Email
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private RoleType roleType;
    private boolean emailVerified;
    private boolean state;
    private String providerId;
    @ElementCollection(fetch = FetchType.EAGER)
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "update_at", updatable = false)
    private LocalDateTime updateAt;

    @Builder
    public Account(String name,
                   String password,
                   String email,
                   RoleType roleType,
                   boolean emailVerified,
                   boolean state) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.roleType = roleType;
        this.emailVerified = emailVerified;
        this.state = state;
    }
}
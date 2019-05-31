package com.toy.springboottoy.account.domain;

import com.toy.springboottoy.common.BaseAuditEntity;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.Path;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseAuditEntity {

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
    private String imgProfileUrl;
    private boolean emailVerified;
    private boolean state;

    @Builder
    public Account(String name,
                   String password,
                   String email,
                   RoleType roleType,
                   String imgProfileUrl,
                   boolean emailVerified,
                   boolean state) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.roleType = roleType;
        this.imgProfileUrl = imgProfileUrl;
        this.emailVerified = emailVerified;
        this.state = state;
    }
}
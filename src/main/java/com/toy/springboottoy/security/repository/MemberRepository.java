package com.toy.springboottoy.security.repository;

import com.toy.springboottoy.security.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    Member findByUsername(String username);
}
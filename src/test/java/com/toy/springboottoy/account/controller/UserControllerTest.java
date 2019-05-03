package com.toy.springboottoy.account.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.toy.springboottoy.account.domain.Role;
import com.toy.springboottoy.account.dto.AccountDto;
import com.toy.springboottoy.common.TestDescription;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @TestDescription("계정생성 시 잘못된 매개변수 들어올 시 실패한다")
    public void createAccount_Bad_Request_Wrong_Input() throws Exception {
        AccountDto.SignUpReq signUpReq = AccountDto.SignUpReq.builder()
                .email("juyoung@gmail.com")
                .password("password")
                .role(Role.USER)
                .build();

        mockMvc.perform(post("/api/account/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(signUpReq)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @TestDescription("계정생성 시 빈 객체 매개변수로 들어오면 실패한다")
    public void createAccount_Bad_Request_Empty_Input() throws Exception {
        AccountDto.SignUpReq signUpReq = AccountDto.SignUpReq.builder().build();

        mockMvc.perform(post("/api/account/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(signUpReq))
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    @TestDescription("요리사 회원가입 정상 데이터 받을 때 성공")
    public void createAccount_for_chef_success() throws Exception {
        AccountDto.SignUpReq newAccount = AccountDto.SignUpReq.builder()
                .userName("chef")
                .password("pass")
                .email("chef@outlook.com")
                .role(Role.CHEF)
                .build();

        mockMvc.perform(post("/api/account/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(newAccount)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("userName").value(Matchers.is("chef")))
                .andExpect(jsonPath("role").value(Role.CHEF.name()))
                .andDo(print())
        ;
    }

    @Test
    @TestDescription("유저 회원가입 정상 데이터 받을 때 성공")
    public void createAccount_for_user_success() throws Exception {
        AccountDto.SignUpReq newAccount = AccountDto.SignUpReq.builder()
                .userName("juyoung")
                .password("test")
                .email("juyoung@gmail.com")
                .role(Role.USER)
                .build();

        mockMvc.perform(post("/api/account/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(newAccount)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("id").value(Matchers.not(0)))
                .andExpect(jsonPath("role").value(Matchers.is(Role.USER.name())))
        ;
    }

    // todo : Fail test - Exception 처리
    @Test(expected = IllegalArgumentException.class)
    @TestDescription("유저 이미 사용중인 이메일일 경우 계정생성 실패")
    public void createAccount_for_user_fail() throws Exception {
        AccountDto.SignUpReq newAccount = AccountDto.SignUpReq.builder()
                .userName("juyoung")
                .password("test")
                .email("email@gmail.com")
                .role(Role.USER)
                .build();

        mockMvc.perform(post("/api/account/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(newAccount)))
                .andDo(print())
        ;
    }

}

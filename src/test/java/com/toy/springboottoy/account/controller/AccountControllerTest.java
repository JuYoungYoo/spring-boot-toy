package com.toy.springboottoy.account.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.toy.springboottoy.account.domain.Account;
import com.toy.springboottoy.account.domain.RoleType;
import com.toy.springboottoy.account.model.SignUpRequest;
import com.toy.springboottoy.common.TestDescription;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static com.toy.springboottoy.account.AccountAbstract.accountReqOf;
import static com.toy.springboottoy.account.AccountAbstract.getUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @TestDescription("계정생성 시 잘못된 매개변수 들어올 시 실패한다")
    public void createAccount_Bad_Request_Wrong_Input() throws Exception {
        SignUpRequest signUpReq = SignUpRequest.builder()
                .email("juyoung@gmail.com")
                .password("password")
                .roleType(RoleType.USER)
                .build();

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(signUpReq)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @TestDescription("계정생성 시 빈 객체 매개변수로 들어오면 실패한다")
    public void createAccount_Bad_Request_Empty_Input() throws Exception {
        SignUpRequest signUpReq = SignUpRequest.builder().build();

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(signUpReq)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @TestDescription("관리자 회원가입 정상 데이터 받을 때 성공")
    public void createAccount_For_Chef_Success() throws Exception {
        String userName = "chef";
        RoleType roleType = RoleType.MANAGER;
        SignUpRequest newAccount = accountReqOf(userName, "manage@outlook.com", roleType);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(newAccount)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("userName").value(Matchers.is(userName)))
                .andExpect(jsonPath("roleType").value(roleType.name()));
    }

    @Test
    @TestDescription("유저 회원가입 정상 데이터 받을 때 성공")
    public void createAccount_For_User_Success() throws Exception {
        SignUpRequest newAccount = accountReqOf("test", "test@gmail.com", RoleType.USER);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(newAccount)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(Matchers.not(0)))
                .andExpect(jsonPath("role").value(Matchers.is(RoleType.USER.name())));
    }

    @Test
    @TestDescription("유저 이미 사용중인 이메일일 경우 계정생성 실패")
    public void createAccount_For_User_Fail() throws Exception {
        Account existAccount = getUser();
        SignUpRequest signUpReq = accountReqOf(existAccount.getName(), existAccount.getEmail());

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(signUpReq)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @TestDescription("Id에 해당하는 유저정보를 반환한다")
    public void getAccount_Info() throws Exception {
        long id = 1L;
        Account expected = getUser();

        mockMvc.perform(get("/api/users/" +id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("userName").value(expected.getName()))
                .andExpect(jsonPath("email").value(expected.getEmail()))
                .andExpect(jsonPath("role").value(expected.getRoleType().name()));
    }

    @Test
    @TestDescription("Id에 해당하는 계정정보가 없을 시 실패한다")
    public void getAccount_Not_Found() throws Exception {
        long id = 9999L;

        mockMvc.perform(get("/api/users/" +id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}

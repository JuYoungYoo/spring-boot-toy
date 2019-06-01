package com.toy.springboottoy.account.controller;


import com.toy.springboottoy.account.domain.Account;
import com.toy.springboottoy.account.domain.RoleType;
import com.toy.springboottoy.account.model.AccountUpdateRequest;
import com.toy.springboottoy.account.model.SignUpRequest;
import com.toy.springboottoy.common.BaseControllerTest;
import com.toy.springboottoy.common.TestDescription;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static com.toy.springboottoy.common.AccountAbstract.accountReqOf;
import static com.toy.springboottoy.common.AccountAbstract.existUser;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountControllerTest extends BaseControllerTest {


    @Test
    @TestDescription("인증 정보 가져오기")
    public void getAccountInfoByMe() throws Exception {
        mockMvc.perform(get("/accounts/me")
                .header(HttpHeaders.AUTHORIZATION, obtainToken(USER_ID, USER_PASSWORD)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("email").value("manager@gmail.com"))
                .andExpect(jsonPath("jti").exists())
        ;
    }

    @Test
    @TestDescription("회원탈퇴")
    public void disabledAccount() throws Exception {
        long id = 1l;
        mockMvc.perform(delete("/accounts/" + id)
                .header(HttpHeaders.AUTHORIZATION, obtainToken(USER_ID, USER_PASSWORD))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isNoContent())
        ;
    }

    @Test
    @TestDescription("비밀번호를 변경한다")
    public void changePassword() throws Exception {
        long id = 1l;
        AccountUpdateRequest.ChangePassword changePwdRequest = AccountUpdateRequest.ChangePassword.builder()
                .password("changepass")
                .build();

        mockMvc.perform(patch("/accounts/" + id)
                .header(HttpHeaders.AUTHORIZATION, obtainToken(USER_ID, USER_PASSWORD))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(changePwdRequest)))
                .andDo(print())
                .andExpect(status().isNoContent())
        ;
    }

    @Test
    @TestDescription("계정생성 시 잘못된 매개변수 들어올 시 실패한다")
    public void createAccount_bad_request_wrong_input_by_null_name() throws Exception {
        SignUpRequest signUpReq = SignUpRequest.builder()
                .email("juyoung@gmail.com")
                .password("password")
                .roleType(RoleType.USER)
                .build();

        mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(signUpReq)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @TestDescription("계정생성 시 빈 객체 매개변수로 들어오면 실패한다")
    public void createAccount_bad_request_empty_input() throws Exception {
        SignUpRequest signUpReq = SignUpRequest.builder().build();
        mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(signUpReq)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @TestDescription("회원가입 정상 데이터 받을 때 성공")
    public void createAccount_For_Chef_Success() throws Exception {
        String userName = "juyoung";
        SignUpRequest newAccount = accountReqOf(userName, "juyoung@gamil.com");

        mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(newAccount)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name").value(is(userName)))
                .andExpect(jsonPath("email").value(newAccount.getEmail()));
    }

    @Test
    @TestDescription("유저 회원가입 정상 데이터 받을 때 성공")
    public void createAccount_For_User_Success() throws Exception {
        String userName = "juyoung";
        SignUpRequest newAccount = accountReqOf(userName, "juyoung@gamil.com");

        mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(newAccount)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(Matchers.not(0)));
    }

    @Test
    @TestDescription("유저 이미 사용중인 이메일일 경우 계정생성 실패")
    public void createAccount_For_User_Fail() throws Exception {
        Account existAccount = existUser();
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .name(existAccount.getName())
                .email(existAccount.getEmail())
                .password(existAccount.getPassword())
                .roleType(existAccount.getRoleType())
                .build();

        mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(signUpRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @TestDescription("Id에 해당하는 유저정보를 반환한다")
    public void getAccount_Info() throws Exception {
        long id = 1L;
        Account expected = existUser();

        mockMvc.perform(get("/accounts/" + id)
                .header(HttpHeaders.AUTHORIZATION, obtainToken(USER_ID, USER_PASSWORD))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(expected.getName()))
                .andExpect(jsonPath("email").value(expected.getEmail()));
    }

    @Test
    @TestDescription("Id에 해당하는 계정정보가 없을 시 실패한다")
    public void getAccount_Not_Found() throws Exception {
        long noneId = 9999L;

        mockMvc.perform(get("/accounts/" + noneId)
                .header(HttpHeaders.AUTHORIZATION, obtainToken(USER_ID, USER_PASSWORD))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
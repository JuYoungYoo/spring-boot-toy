package com.toy.springboottoy.account.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toy.springboottoy.account.domain.Role;
import com.toy.springboottoy.account.dto.SessionDto;
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

import static com.toy.springboottoy.error.ErrorCode.ACCOUNT_NOT_FOUND;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SessionControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @TestDescription("로그인 성공")
    public void signIn_By_Success() throws Exception {
        String email = "user@gmail.com";
        String password = "password";

        SessionDto.signInReq sessionDto = SessionDto.signInReq.builder()
                .email(email)
                .password(password)
                .build();

        mockMvc.perform(post("/api/account/session")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(sessionDto)))
                .andDo(print())
                .andExpect(jsonPath("id").value(Matchers.not(0)))
                .andExpect(jsonPath("email").value(email))
                .andExpect(status().isOk());
    }

    @Test
    @TestDescription("로그인 실패")
    public void signIn_By_Fail() throws Exception {
        String email = "nonEmail@gmail.com";
        String password = "password";

        SessionDto.signInReq sessionDto = SessionDto.signInReq.builder()
                .email(email)
                .password(password)
                .build();
        mockMvc.perform(post("/api/account/session")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(sessionDto)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("code").value(ACCOUNT_NOT_FOUND.getCode()));
    }


}

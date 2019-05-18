package com.toy.springboottoy.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toy.springboottoy.account.model.SessionDto;
import com.toy.springboottoy.account.model.SignInRequest;
import com.toy.springboottoy.common.TestDescription;
import com.toy.springboottoy.config.AppProperties;
import io.restassured.RestAssured;
import org.junit.Before;
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
import static com.toy.springboottoy.security.model.JwtAuthenticationResponse.TOKEN_TYPE;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

    private final String USER_NAME = "user@email.com";
    private final String USER_PASSWORRD = "user";

    @Before
    public void setup() {
        RestAssured.port = 8080;
    }

    @Test
    public void login_page() {
        given()
                .when()
                    .get("/session")
                .then()
                    .statusCode(200)
                    .contentType("text/html")
                    .body(containsString("Login page"));
    }

    @Test
    @TestDescription("로그인 성공 시 Jwt 토큰 반환한다")
    public void signIn_By_Success() throws Exception {
        SignInRequest signInRequest = getSignInRequest(USER_NAME, USER_PASSWORRD);

        mockMvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signInRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("accessToken").exists())
                .andExpect(jsonPath("tokenType").value(TOKEN_TYPE))
                ;
    }

    private SignInRequest getSignInRequest(String email,
                                           String password) {
        return SignInRequest.builder()
                    .email(email)
                    .password(password)
                    .build();
    }

    @Test
    @TestDescription("로그인 실패")
    public void signIn_By_Fail() throws Exception {
        String email = "nonEmail@gmail.com";
        String password = "password";

        SessionDto.SignInReq sessionDto = SessionDto.SignInReq.builder()
                .email(email)
                .password(password)
                .build();
        mockMvc.perform(post("/api/session")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(sessionDto)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("code").value(ACCOUNT_NOT_FOUND.getCode()));
    }


}

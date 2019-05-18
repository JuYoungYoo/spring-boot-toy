package com.toy.springboottoy.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.toy.springboottoy.account.model.SignInRequest;
import com.toy.springboottoy.common.TestDescription;
import com.toy.springboottoy.config.AppProperties;
import io.restassured.internal.mapping.Jackson2Mapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.toy.springboottoy.security.model.JwtAuthenticationResponse.TOKEN_TYPE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SecurityConfigTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    AppProperties appProperties;

    private String USER_NAME ;
    private String USER_PASSWORD;

    @Before
    public void setUp() {
        USER_NAME = appProperties.getUserId();
        USER_PASSWORD = appProperties.getUserPassword();
    }

    @Test @TestDescription("인증 된 토큰일 경우 리소스 접근에 성공한다")
    public void has_token_then_passes_through_the_authentication() throws Exception {
            String bearerToken = obtainToken(USER_NAME, USER_PASSWORD);
            mockMvc.perform(get("/users/1")
                    .header(HttpHeaders.AUTHORIZATION, bearerToken))
                    .andDo(print())
                    .andExpect(status().isOk())
            ;
    }

    private String obtainToken(String email, String password) throws Exception {
        SignInRequest signInRequest = SignInRequest.builder()
                .email(email)
                .password(password)
                .build();

        ResultActions perform = mockMvc.perform(post("/session")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(signInRequest)));

        String response = perform.andReturn().getResponse().getContentAsString();
        JsonParser jsonParser = new JsonParser();
        JsonObject object = jsonParser.parse(response).getAsJsonObject();
        return "Bearer " + object.get("accessToken").toString().replace("\"", "");
    }

}
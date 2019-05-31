package com.toy.springboottoy.config;

import com.toy.springboottoy.common.AppProperties;
import com.toy.springboottoy.common.AuthProperties;
import com.toy.springboottoy.common.TestDescription;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
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
    private MockMvc mockMvc;
    @Autowired
    private AppProperties appProperties;
    @Autowired
    private AuthProperties authProperties;

    @Test
    @TestDescription("토큰을 생성한다")
    public void login() throws Exception {
        mockMvc.perform(post("/oauth/token")
                .with(httpBasic(authProperties.getClientId(), authProperties.getClientSecret()))
                .param("username", appProperties.getUserId())
                .param("password", appProperties.getUserPassword())
                .param("grant_type", "password"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists());
    }

    @Test @TestDescription("인증 된 토큰일 경우 리소스 접근에 성공한다")
    public void has_token_then_passes_through_the_authentication() throws Exception {
        String bearerToken = obtainToken(appProperties.getUserId(), appProperties.getUserPassword());
        mockMvc.perform(get("/users/1")
                .header(HttpHeaders.AUTHORIZATION, bearerToken))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test @TestDescription("토근 없을 시 접속 불가")
    public void another_resource_connected_token() throws Exception {
        String id = "1";
        mockMvc.perform(get("/users/" + id))
                .andDo(print())
                .andExpect(status().isUnauthorized())
        ;
    }

    private String obtainToken(String userName, String password) throws Exception {
        ResultActions perform = mockMvc.perform(post("/oauth/token")
                .with(httpBasic(authProperties.getClientId(), authProperties.getClientSecret()))
                .param("username", userName)
                .param("password", password)
                .param("grant_type", "password"));

        String response = perform.andReturn().getResponse().getContentAsString();
        Jackson2JsonParser parser = new Jackson2JsonParser();
        String access_token = parser.parseMap(response).get("access_token").toString();
        return "Bearer " + access_token;
    }
}
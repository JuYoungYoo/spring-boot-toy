package com.toy.springboottoy.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toy.springboottoy.config.AppProperties;
import com.toy.springboottoy.config.AuthProperties;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Ignore
public class BaseControllerTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected AppProperties appProperties;
    @Autowired
    protected AuthProperties authProperties;

    protected String CLIENT_ID;
    protected String CLIENT_SECRET;
    protected String USER_NAME;
    protected String USER_PASSWORD;

    @Before
    public void setUp() {
        CLIENT_ID = authProperties.getClientId();
        CLIENT_SECRET = authProperties.getClientSecret();
        USER_NAME = appProperties.getUserId();
        USER_PASSWORD = appProperties.getUserPassword();
    }

    protected String obtainToken(String userName,
                                 String password) throws Exception {
        ResultActions perform = mockMvc.perform(post("/oauth/token")
                .with(httpBasic(CLIENT_ID, CLIENT_SECRET))
                .param("username", userName)
                .param("password", password)
                .param("grant_type", "password"));

        String response = perform.andReturn().getResponse().getContentAsString();
        Jackson2JsonParser parser = new Jackson2JsonParser();
        String access_token = parser.parseMap(response).get("access_token").toString();
        return "Bearer " + access_token;
    }

}

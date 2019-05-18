package com.toy.springboottoy.security.config;

import com.toy.springboottoy.common.TestDescription;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class AuthorizationServerConfigTest {


    @Autowired
    private MockMvc mockMvc;

    @Test
    @TestDescription("토큰 발급에 성공한다")
    public void Create_Token_Success() throws Exception {
        String password = "juyoung-password";
        ResultActions perform = mockMvc.perform(post("/oauth/token")
                .with(httpBasic("juyoung-client", password))
                .param("username", "juyoung")
                .param("password", "pass")
                .param("grant_type", "password"))
                .andDo(print())
                .andExpect(jsonPath("access_token").exists())
                .andExpect(jsonPath("scope").value("read write trust"))
                ;
//        String response = perform.andReturn().getResponse().getContentAsString();
//        Jackson2JsonParser parser = new Jackson2JsonParser();
//        parser.parseMap(response).get("access_token").toString();
//        return access_token;

    }
}
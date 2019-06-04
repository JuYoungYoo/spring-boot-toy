package com.toy.springboottoy.config;

import com.toy.springboottoy.account.domain.Account;
import com.toy.springboottoy.account.domain.RoleType;
import com.toy.springboottoy.account.service.AccountService;
import com.toy.springboottoy.common.AppProperties;
import com.toy.springboottoy.common.TestDescription;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    private AccountService accountService;

    @Test
    @TestDescription("토큰을 생성한다")
    public void getAccessToken() throws Exception {
        String email = "test@email.com";
        String originPassword = "pass";
        Account account = Account.builder()
                .name("user")
                .email(email)
                .password(originPassword)
                .roleType(RoleType.MANAGER)
                .state(true)
                .emailVerified(true)
                .build();
        accountService.signUp(account);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("username", email);
        params.add("password", originPassword);

        mockMvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic(appProperties.getClientId(), appProperties.getClientSecret()))
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("access_token").isNotEmpty())
                .andExpect(jsonPath("token_type").value("bearer"))
                .andExpect(jsonPath("refresh_token").isNotEmpty())
                .andExpect(jsonPath("expires_in").isNumber())
                .andExpect(jsonPath("scope").value("read write trust"))
        ;
    }

    @Test @TestDescription("인증 된 토큰일 경우 리소스 접근에 성공한다")
    public void has_token_then_passes_through_the_authentication() throws Exception {
        mockMvc.perform(get("/accounts/1")
                .header(HttpHeaders.AUTHORIZATION, obtainToken()))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test @TestDescription("토근 없을 시 접속 불가")
    public void another_resource_connected_token() throws Exception {
        mockMvc.perform(delete("/accounts"))
                .andDo(print())
                .andExpect(status().isUnauthorized())
        ;
    }

    private String obtainToken() throws Exception {
        ResultActions perform = mockMvc.perform(post("/oauth/token")
                .with(httpBasic(appProperties.getClientId(), appProperties.getClientSecret()))
                .param("username", appProperties.getUserId())
                .param("password", appProperties.getUserPassword())
                .param("grant_type", "password"));

        String response = perform.andReturn().getResponse().getContentAsString();
        Jackson2JsonParser parser = new Jackson2JsonParser();
        String access_token = parser.parseMap(response).get("access_token").toString();
        return "Bearer " + access_token;
    }
}

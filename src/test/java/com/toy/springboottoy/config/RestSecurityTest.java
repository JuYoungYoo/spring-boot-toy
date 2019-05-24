package com.toy.springboottoy.config;

import com.toy.springboottoy.common.TestDescription;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestSecurityTest {

    @Autowired
    AppProperties appProperties;

    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8081;
    }

    @Test
    public void 기본_path로_접근하면_index_html_호출된다() throws Exception {
        given()
                .when()
                    .get("/login/oauth2/code/google")
                .then()
                    .log().all()
                ;
    }

    @Test @TestDescription("외부 리소스에서 토큰으로 인증 통과한 후 리소스 접속에 성공한다")
    public void when_another_resources_authentization_succuess() {
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "password");
        params.put("username", appProperties.getUserId());
        params.put("password", appProperties.getUserPassword());

        given()
                .auth()
                    .preemptive()
                        .oauth2(obtainAccessToken(appProperties.getUserId(), appProperties.getUserPassword()))
                .and()
                    .when()
                        .get("http://localhost:8080/users/1")
                .then()
                    .log().all()
                    .statusCode(200)
                    .body("userName",equalTo("admin"))
        ;
    }

    @Test @TestDescription("외부 리소스에서 토큰 요청")
    public void when_another_resources_request_token() {
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "password");
        params.put("username", appProperties.getUserId());
        params.put("password", appProperties.getUserPassword());

        given()
                .auth()
                    .preemptive()
                        .basic(appProperties.getClientId(), appProperties.getClientSecret())
                .and()
                .with().params(params)
                    .when()
                .post("http://localhost:8080/oauth/token")
                .andReturn().jsonPath().get("access_token")
        ;
    }

    private String obtainAccessToken(String username,
                                     String password) {
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "password");
        params.put("username", username);
        params.put("password", password);

        Response response =
                given()
                        .auth()
                        .preemptive()
                        .basic(appProperties.getClientId(), appProperties.getClientSecret())
                        .and()
                        .with().params(params)
                        .when()
                        .post("http://localhost:8080/oauth/token");
        return response.jsonPath().getString("access_token");
    }
}
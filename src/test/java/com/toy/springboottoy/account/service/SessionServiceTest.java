package com.toy.springboottoy.account.service;

import com.toy.springboottoy.account.domain.RoleType;
import com.toy.springboottoy.account.model.SessionDto;
import com.toy.springboottoy.account.reepository.AccountRepository;
import com.toy.springboottoy.common.TestDescription;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ActiveProfiles
@RunWith(MockitoJUnitRunner.class)
public class SessionServiceTest {

    @InjectMocks
    SessionService sessionService;
    @Mock
    AccountRepository accountRepository;

    @Test
    @TestDescription("입력값에 일치하는 계정이 존재하는 경우 계정정보를 반환한다")
    public void signIn_Success() {
        // given
        String email = "user@gmail.com";
        SessionDto.SignInReq existSessionSeq = SessionDto.SignInReq.builder()
                .email(email)
                .password("password")
                .build();
        SessionDto.SignInRes expected = SessionDto.SignInRes.builder()
                .id(1)
                .email(email)
                .roleType(RoleType.USER)
                .state(true)
                .build();
        given(accountRepository.findByEmailAndPassword(any(), any())).willReturn(Optional.of(expected));

        // when
        SessionDto.SignInRes result = sessionService.signIn(existSessionSeq);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo(existSessionSeq.getEmail());
    }

    @Test(expected = IllegalArgumentException.class)
    @TestDescription("등록된 계정이 아닌경우 에러")
    public void signIn_Fail() {
        SessionDto.SignInReq signInReq = SessionDto.SignInReq.builder().build();
        given(accountRepository.findByEmailAndPassword(any(), any())).willReturn(Optional.empty());
        sessionService.signIn(signInReq);
    }
}
package com.toy.springboottoy.account.service;

import com.toy.springboottoy.account.domain.Account;
import com.toy.springboottoy.account.domain.Role;
import com.toy.springboottoy.account.dto.AccountDto;
import com.toy.springboottoy.account.reepository.UserRepository;
import com.toy.springboottoy.common.TestDescription;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

import static com.toy.springboottoy.account.AccountAbstract.accountOf;
import static com.toy.springboottoy.account.AccountAbstract.accountReqOf;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @Test(expected = IllegalArgumentException.class)
    @TestDescription("이메일 중복일 경우 회원가입 실패")
    public void duplicateEmail() {
        String userName = "juyoung";
        String email = "juyoung@gmail.com";
        Role role = Role.USER;

        AccountDto.SignUpReq account = accountReqOf(userName, email, role);

        given(userRepository.existsByEmail(any())).willReturn(true);
        userService.signUp(account);
    }

    @Test
    @TestDescription("회원가입 성공")
    public void signUpSuccess() {
        String userName = "juyoung";
        String email = "juyoung@gmail.com";
        Role role = Role.USER;

        given(userRepository.existsByEmail(any())).willReturn(false);
        given(userRepository.save(any())).willReturn(accountOf(userName, email, role));

        Account newAccount = userService.signUp(accountReqOf(userName, email, role));

        assertThat(newAccount.getUserName()).isEqualTo(userName);
        assertThat(newAccount.getRole()).isEqualTo(role);
    }
}
package com.toy.springboottoy.account.service;

import com.toy.springboottoy.account.domain.Account;
import com.toy.springboottoy.account.domain.Role;
import com.toy.springboottoy.account.reepository.AccountRepository;
import com.toy.springboottoy.common.TestDescription;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

import static com.toy.springboottoy.account.AccountAbstract.accountOf;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;
    @Mock
    private AccountRepository accountRepository;

    @Test(expected = IllegalArgumentException.class)
    @TestDescription("이메일 중복일 경우 회원가입 실패")
    public void duplicateEmail() {
        String userName = "juyoung";
        String email = "juyoung@gmail.com";
        Role role = Role.USER;

        Account account = accountOf(userName, email, role);

        given(accountRepository.existsByEmail(any())).willReturn(true);
        accountService.signUp(account);
    }

    @Test
    @TestDescription("회원가입 성공")
    public void signUpSuccess() {
        String userName = "juyoung";
        String email = "juyoung@gmail.com";
        Role role = Role.USER;

        given(accountRepository.existsByEmail(any())).willReturn(false);
        given(accountRepository.save(any())).willReturn(Account.builder().build());

        Account newAccount = accountService.signUp(accountOf(userName, email, role));

        assertThat(newAccount.getUserName()).isEqualTo(userName);
        assertThat(newAccount.getRole()).isEqualTo(role);
    }
}
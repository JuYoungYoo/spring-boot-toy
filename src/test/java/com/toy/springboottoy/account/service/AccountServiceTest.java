package com.toy.springboottoy.account.service;

import com.toy.springboottoy.account.domain.Account;
import com.toy.springboottoy.account.domain.RoleType;
import com.toy.springboottoy.account.exception.EmailDuplicationException;
import com.toy.springboottoy.account.exception.AccountNotFoundException;
import com.toy.springboottoy.account.model.AccountUpdateRequest;
import com.toy.springboottoy.account.model.SignUpRequest;
import com.toy.springboottoy.account.reepository.AccountRepository;
import com.toy.springboottoy.common.TestDescription;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static com.toy.springboottoy.common.AccountAbstract.accountOf;
import static com.toy.springboottoy.common.AccountAbstract.accountReqOf;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AccountRepository accountRepository;

    @Test
    public void change_password() {
        Account account = accountOf("manager", "pass", "manager@gmail.com");

        AccountUpdateRequest.ChangePassword updateRequest = AccountUpdateRequest.ChangePassword.builder()
                .password("passchange")
                .build();

        given(accountRepository.findById(any())).willReturn(Optional.ofNullable(account));
        account.changePassword(updateRequest);
        given(accountRepository.save(account)).willReturn(account);

        assertThat(passwordEncoder.matches(account.getPassword(), updateRequest.getPassword())).isTrue();
    }

    @Test(expected = EmailDuplicationException.class)
    @TestDescription("이메일 중복일 경우 회원가입 실패")
    public void duplicate_Email() {
        String userName = "juyoung";
        String email = "juyoung@gmail.com";

        SignUpRequest account = accountReqOf(userName, email);

        given(accountRepository.existsByEmail(any())).willReturn(true);
        accountService.signUp(account);
    }

    @Test
    @TestDescription("회원가입 성공")
    public void signUp_Success() {
        String userName = "juyoung";
        String email = "juyoung@gmail.com";

        given(accountRepository.existsByEmail(any())).willReturn(false);
        given(accountRepository.save(any())).willReturn(accountOf(userName, email));

        Account newAccount = accountService.signUp(accountReqOf(userName, email));

        assertThat(newAccount.getName()).isEqualTo(userName);
    }

    @Test
    @TestDescription("Id에 해당하는 계정정보 반환")
    public void findById_Success() {
        Long id = 1L;
        String email = "user@gmail.com";
        Account expected = accountOf("juyoung", email);
        given(accountRepository.findById(id)).willReturn(Optional.of(expected));

        Account result = accountService.findById(id);

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo(email);
    }

    @Test(expected = AccountNotFoundException.class)
    @TestDescription("id에 해당하는 계정이 없을 경우 에러")
    public void findById_Fail() {
        given(accountRepository.findById(any())).willReturn(Optional.empty());
        accountService.findById(1L);
    }
}
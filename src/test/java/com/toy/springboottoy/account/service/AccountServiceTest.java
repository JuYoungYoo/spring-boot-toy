package com.toy.springboottoy.account.service;

import com.toy.springboottoy.account.AccountService;
import com.toy.springboottoy.account.domain.Account;
import com.toy.springboottoy.account.domain.RoleType;
import com.toy.springboottoy.account.exception.AccountNotFoundException;
import com.toy.springboottoy.account.exception.EmailDuplicationException;
import com.toy.springboottoy.account.AccountRepository;
import com.toy.springboottoy.common.TestDescription;
import com.toy.springboottoy.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
@Import(AppConfig.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AccountRepository accountRepository;

    @Test
    @Description("로그인 성공")
    public void loadUserByUsername() {
        Account account = account();
        given(accountRepository.findByEmail(any())).willReturn(Optional.ofNullable(account));
        UserDetails userDetails = accountService.loadUserByUsername(account.getEmail());

        assertThat(userDetails.getUsername()).isEqualTo(account.getEmail());
        assertThat(userDetails.getPassword()).isEqualTo(account.getPassword());
    }

    @Test(expected = EmailDuplicationException.class)
    @TestDescription("이메일 중복일 경우 회원가입 실패")
    public void duplicate_Email() {
        given(accountRepository.existsByEmail(any())).willReturn(true);
        accountService.signUp(account());
    }

    @Test
    @TestDescription("회원가입 성공")
    public void signUp_Success() {
        Account account = account();

        given(accountRepository.existsByEmail(any())).willReturn(false);
        given(accountRepository.save(any())).willReturn(account);

        Account newAccount = accountService.signUp(account);

        assertThat(newAccount).isNotNull();
        assertThat(newAccount.getName()).isEqualTo(account.getName());
    }

    @Test
    @TestDescription("Id에 해당하는 계정정보 반환")
    public void findById_Success() {
        Account expectedAccount = account();

        given(accountRepository.findById(anyLong())).willReturn(Optional.of(expectedAccount));
        Account result = accountService.findById(1l);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(expectedAccount);
    }

    @Test(expected = AccountNotFoundException.class)
    @TestDescription("id에 해당하는 계정이 없을 경우 에러")
    public void findById_Fail() {
        given(accountRepository.findById(any())).willReturn(Optional.empty());
        accountService.findById(23423423L);
    }

    private Account account() {
        return Account.builder()
                .name("user")
                .email("user@gmail.com")
                .password("pass")
                .roleType(RoleType.MANAGER)
                .state(true)
                .emailVerified(true)
                .build();
    }
}
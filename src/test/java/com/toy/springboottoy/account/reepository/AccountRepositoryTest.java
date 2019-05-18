package com.toy.springboottoy.account.reepository;

import com.toy.springboottoy.account.domain.Account;
import com.toy.springboottoy.account.model.SessionDto;
import com.toy.springboottoy.common.TestDescription;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static com.toy.springboottoy.account.AccountAbstract.accountOf;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class AccountRepositoryTest {

    private Account account;

    @Autowired
    AccountRepository accountRepository;

    @Before
    public void setUp() {
        account = accountOf("juyoung", "juyoung@gmail.com", "password");
    }

    @Test
    @TestDescription("계정생성 성공")
    public void saveAccount() {
        String userName = account.getName();
        String email = account.getEmail();

        Account newAccount = accountRepository.save(account);


        assertThat(newAccount.getName()).isEqualTo(userName);
        assertThat(newAccount.getEmail()).isEqualTo(email);
    }

    @Test
    @TestDescription("이미 등록된 이메일일 경우 true 반환한다")
    public void existsByEmail_Return_True() {
        String existEmail = account.getEmail();
        saveAccount();
        boolean result = accountRepository.existsByEmail(existEmail);
        assertThat(result).isTrue();
    }

    @Test
    @TestDescription("이미 등록된 이메일일 경우 false 반환한다")
    public void existsByEmail_Return_Fail() {
        String nonExistEmail = "noEmail@gmail.com";
        saveAccount();
        boolean result = accountRepository.existsByEmail(nonExistEmail);
        assertThat(result).isFalse();
    }

    @Test
    @TestDescription("id에 해당하는 계정정보 반환한다")
    public void findById_Return_Account() {
        Long id = 1L;
        Optional<Account> account = accountRepository.findById(id);
        assertThat(account).isNotEmpty();
    }

    @Test
    @TestDescription("계정이 존재할 때 성공한다")
    public void findByEmailAndPassword_Success() {
        String email = "user@gmail.com";
        String password = "password";

        Optional<SessionDto.SignInRes> findSessionRes = accountRepository.findByEmailAndPassword(email, password);
        SessionDto.SignInRes sessionRes = findSessionRes.get();

        assertThat(sessionRes.getEmail()).isEqualTo(email);
        assertThat(sessionRes.getRole()).isNotNull();
    }
}
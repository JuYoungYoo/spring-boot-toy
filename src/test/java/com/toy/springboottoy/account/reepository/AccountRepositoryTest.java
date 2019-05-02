package com.toy.springboottoy.account.reepository;

import com.toy.springboottoy.account.domain.Account;
import com.toy.springboottoy.account.domain.Role;
import com.toy.springboottoy.common.TestDescription;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static com.toy.springboottoy.account.AccountAbstract.createAccount;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class AccountRepositoryTest {

    private Account account;

    @Autowired
    AccountRepository accountRepository;

    @Test
    @TestDescription("계정생성 성공")
    public void saveAccount() {
        String userName = "juyoung";
        String email = "juyoung@gmail.com";
        String password = "password";
        account = createAccount(userName, password, email, true, false, Role.USER);

        Account newAccount = accountRepository.save(account);

        assertThat(newAccount.getUserName()).isEqualTo(userName);
        assertThat(newAccount.getEmail()).isEqualTo(email);
    }

    @Test
    @TestDescription("이미 등록된 이메일일 경우 true 반환한다")
    public void existsByEmailReturnTrue() {
        String existEmail = account.getEmail();
        setAccount();

        boolean result = accountRepository.existsByEmail(existEmail);

        assertThat(result).isTrue();
    }

    @Test
    @TestDescription("이미 등록된 이메일일 경우 false 반환한다")
    public void existsByEmailReturnFail() {
        String nonExistEmail = "noEmail@gmail.com";

        boolean result = accountRepository.existsByEmail(nonExistEmail);

        assertThat(result).isFalse();
    }

    private void setAccount() {
        this.saveAccount();
    }
}
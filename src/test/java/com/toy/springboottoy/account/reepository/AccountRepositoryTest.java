package com.toy.springboottoy.account.reepository;

import com.toy.springboottoy.account.domain.Account;
import com.toy.springboottoy.account.domain.RoleType;
import com.toy.springboottoy.common.TestDescription;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Before
    public void setUp() throws Exception {
        accountRepository.deleteAll();
    }

    @Test
    @TestDescription("계정생성 성공")
    public void saveAccount() {
        Account account = account();
        String email = account.getEmail();
        Account newAccount = accountRepository.save(account);
        assertThat(newAccount).isNotNull();
        assertThat(newAccount.getEmail()).isEqualTo(email);
    }

    private Account account() {
        return Account.builder()
                .name("juyoung")
                .email("juyoung@gmail.com")
                .password("pass")
                .roleType(RoleType.MANAGER)
                .state(true)
                .emailVerified(true)
                .build();
    }

    @Test
    @TestDescription("이미 등록된 이메일일 경우 true 반환한다")
    public void duplicatedEmail_Return_True() {
        Account existingUser = accountRepository.save(account());
        boolean result = accountRepository.existsByEmail(existingUser.getEmail());
        assertThat(result).isTrue();
    }

    @Test
    @TestDescription("id에 해당하는 계정정보 반환한다")
    public void findById_Return_Account() {
        Account saveAccount = accountRepository.save(account());
        long id = saveAccount.getId();

        Optional<Account> account = accountRepository.findById(id);

        assertThat(account).isNotEmpty();
        assertThat(account.get().getId()).isEqualTo(id);
    }

    @Test
    @TestDescription("email에 해당하는 계정정보 반환한다")
    public void findByEmail_Return_Account() {
        Account saveAccount = accountRepository.save(account());
        String email = saveAccount.getEmail();

        Optional<Account> account = accountRepository.findByEmail(email);

        assertThat(account).isNotEmpty();
        assertThat(account.get().getEmail()).isEqualTo(email);
    }
}
package com.toy.springboottoy.account.service;

import com.toy.springboottoy.account.domain.Account;
import com.toy.springboottoy.account.domain.AccountAdapter;
import com.toy.springboottoy.account.exception.AccountNotFoundException;
import com.toy.springboottoy.account.exception.EmailDuplicationException;
import com.toy.springboottoy.account.model.AccountUpdateRequest;
import com.toy.springboottoy.account.reepository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    public Account signUp(Account account) {
        String email = account.getEmail();
        validateDuplicatedEmail(email);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    private void validateDuplicatedEmail(String email) {
        boolean existsByEmail = accountRepository.existsByEmail(email);
        if (existsByEmail) {
            throw new EmailDuplicationException(email);
        }
    }

    public Account findById(Long id) {
        return findAccountById(id);
    }

    public void changePassword(final long id,
                               AccountUpdateRequest.ChangePassword changePasswordRequest) {
        Account account = findAccountById(id);
        changePasswordRequest.encode(passwordEncoder);
        account.changePassword(changePasswordRequest);
        accountRepository.save(account);
    }

    public boolean deleteAccount(long id) {
        Account account = findAccountById(id);
        account.disabled();
        return isDisabledAccount(accountRepository.save(account));
    }

    private boolean isDisabledAccount(Account disabledAccount) {
        return !disabledAccount.isState();
    }

    private Account findAccountById(long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new AccountNotFoundException(email));
        return new AccountAdapter(account);
    }
}

package com.toy.springboottoy.account.service;

import com.toy.springboottoy.account.domain.Account;
import com.toy.springboottoy.account.dto.AccountDto;
import com.toy.springboottoy.account.reepository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public UserService(ModelMapper modelMapper,
                       UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    public Account signUp(AccountDto.SignUpReq dto){
        if(userRepository.existsByEmail(dto.getEmail())){
            throw new IllegalArgumentException("중복된 이메일입니다.");
        }
        return userRepository.save(dto.toEntity());
    }
}

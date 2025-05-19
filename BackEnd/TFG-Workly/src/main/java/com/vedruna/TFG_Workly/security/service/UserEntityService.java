package com.vedruna.TFG_Workly.security.service;

import com.vedruna.TFG_Workly.security.auth.dto.UserRegisterDTO;
import com.vedruna.TFG_Workly.security.model.UserAuthority;
import com.vedruna.TFG_Workly.security.model.UserEntity;
import com.vedruna.TFG_Workly.security.UserEntityRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserEntityService {
    private final UserEntityRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserEntityService(UserEntityRepository repository, PasswordEncoder passwordEncoder){
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<UserEntity> findByUsername(String username){
        return this.repository.findByUsername(username);
    }

    public UserEntity save(UserRegisterDTO userDTO){
        UserEntity user = new UserEntity(
                null,
                userDTO.username(),
                passwordEncoder.encode(userDTO.password()),
                userDTO.email(),
                List.of(UserAuthority.ROLE_USER)
        );
        return  this.repository.save(user);
    }
}
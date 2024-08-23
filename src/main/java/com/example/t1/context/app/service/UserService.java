package com.example.t1.context.app.service;

import com.example.t1.context.app.model.User;
import com.example.t1.context.app.model.enums.UserRole;
import com.example.t1.context.app.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    @PostConstruct
    private void initializeUsers() {
        if (userRepository.findByLogin("user").isEmpty()) {
            var user = User.builder()
                    .id(1L)
                    .login("user")
                    .password("$2a$12$nAKygre7weGygy2JOPEV8ugWoXMi7asWbUrELroP619ODGtR0ccvW")
                    .role(UserRole.VOTER)
                    .build();
            userRepository.save(user);
        }
        if (userRepository.findByLogin("admin").isEmpty()) {
            var admin = User.builder()
                    .id(2L)
                    .login("admin")
                    .password("$2a$12$nAKygre7weGygy2JOPEV8ugWoXMi7asWbUrELroP619ODGtR0ccvW")
                    .role(UserRole.ADMIN)
                    .build();
            userRepository.save(admin);
        }
    }

    public User getByLogin(String login) throws UsernameNotFoundException {
        final Optional<User> user = userRepository.findByLogin(login);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UsernameNotFoundException("User not found");
    }

}

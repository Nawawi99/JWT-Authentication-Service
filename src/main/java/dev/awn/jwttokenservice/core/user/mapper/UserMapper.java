package dev.awn.jwttokenservice.core.user.mapper;

import dev.awn.jwttokenservice.core.user.dto.SignUpRequest;
import dev.awn.jwttokenservice.core.user.dto.UserDTO;
import dev.awn.jwttokenservice.core.user.model.Role;
import dev.awn.jwttokenservice.core.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User toEntity(SignUpRequest signUpRequest) {
        return User.builder()
                   .firstName(signUpRequest.getFirstName())
                   .lastName(signUpRequest.getLastName())
                   .email(signUpRequest.getEmail())
                   .password(passwordEncoder.encode(signUpRequest.getPassword()))
                   .role(Role.ROLE_USER)
                   .build();
    }

    public UserDTO toDTO(User user) {
        return UserDTO.builder()
                      .id(user.getId())
                      .firstName(user.getFirstName())
                      .lastName(user.getLastName())
                      .email(user.getEmail())
                      .role(user.getRole())
                      .build();
    }
}

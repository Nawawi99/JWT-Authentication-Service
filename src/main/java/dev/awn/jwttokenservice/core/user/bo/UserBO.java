package dev.awn.jwttokenservice.core.user.bo;

import dev.awn.jwttokenservice.common.exception.BadRequestException;
import dev.awn.jwttokenservice.common.security.utils.JwtUtils;
import dev.awn.jwttokenservice.core.user.dto.JwtAuthenticationResponse;
import dev.awn.jwttokenservice.core.user.dto.SignInRequest;
import dev.awn.jwttokenservice.core.user.dto.SignUpRequest;
import dev.awn.jwttokenservice.core.user.dto.UserDTO;
import dev.awn.jwttokenservice.core.user.mapper.UserMapper;
import dev.awn.jwttokenservice.core.user.model.User;
import dev.awn.jwttokenservice.core.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserBO {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final JwtUtils jwtUtils;

    @Autowired
    public UserBO(UserRepository userRepository,
                  @Lazy AuthenticationManager authenticationManager,
                  UserMapper userMapper,
                  JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
        this.jwtUtils = jwtUtils;
    }

    public JwtAuthenticationResponse signup(SignUpRequest request) {
        User user = userMapper.toEntity(request);

        User createdUser = userRepository.save(user);

        String jwt = jwtUtils.generateToken(createdUser);

        UserDTO userDTO = userMapper.toDTO(user);

        return JwtAuthenticationResponse.builder()
                                        .userDTO(userDTO)
                                        .token(jwt)
                                        .build();
    }

    public JwtAuthenticationResponse signin(SignInRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                                  .orElseThrow(() -> new BadRequestException("Email doesn't exist"));

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        String jwt = jwtUtils.generateToken(user);

        UserDTO userDTO = userMapper.toDTO(user);

        return JwtAuthenticationResponse.builder()
                                        .userDTO(userDTO)
                                        .token(jwt)
                                        .build();
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
}

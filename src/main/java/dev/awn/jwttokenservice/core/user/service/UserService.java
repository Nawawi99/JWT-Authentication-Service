package dev.awn.jwttokenservice.core.user.service;

import dev.awn.jwttokenservice.core.user.dto.JwtAuthenticationResponse;
import dev.awn.jwttokenservice.core.user.dto.SignInRequest;
import dev.awn.jwttokenservice.core.user.dto.SignUpRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    /*
        Authentication Services
     */
    UserDetailsService userDetailsService();

    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SignInRequest request);
}
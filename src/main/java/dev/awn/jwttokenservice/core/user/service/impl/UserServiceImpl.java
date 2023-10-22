package dev.awn.jwttokenservice.core.user.service.impl;

import dev.awn.jwttokenservice.common.exception.BadRequestException;
import dev.awn.jwttokenservice.core.user.bo.UserBO;
import dev.awn.jwttokenservice.core.user.dto.JwtAuthenticationResponse;
import dev.awn.jwttokenservice.core.user.dto.SignInRequest;
import dev.awn.jwttokenservice.core.user.dto.SignUpRequest;
import dev.awn.jwttokenservice.core.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserBO userBO;


    public JwtAuthenticationResponse signup(SignUpRequest request) {
        String email = request.getEmail();

        if(userBO.emailExists(email)) {
            throw new BadRequestException(email + " is already used");
        }

        return userBO.signup(request);
    }


    public JwtAuthenticationResponse signin(SignInRequest request) {
        return userBO.signin(request);
    }

}

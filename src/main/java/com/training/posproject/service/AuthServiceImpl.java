package com.training.posproject.service;

import com.training.posproject.security.jwt.JwtProvider;
import com.training.posproject.security.message.request.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements  AuthService{

    @Autowired
    AuthenticationManager authenticationManager;


    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;


    public String authenticateUser(LoginForm loginRequest){
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);


            return  jwtProvider.generateJwtToken(authentication);

    }


}

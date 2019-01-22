package com.training.posproject.controllers;

import com.training.posproject.repository.ItemRepository;
import com.training.posproject.repository.UserRepository;
import com.training.posproject.security.jwt.JwtProvider;
import com.training.posproject.security.message.request.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600,allowCredentials="true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemRepository itemRepository;


    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;


    @PostMapping("/signIn")
    @ResponseBody
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest, HttpServletResponse response) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);

        Cookie authCookie = new Cookie("posAuthCookie", jwt);
        authCookie.setHttpOnly(true);
        authCookie.setPath("/api");
        authCookie.setMaxAge(2147483647);
        response.addCookie(authCookie);

        return ResponseEntity.ok("Authentication Successful");
    }


    @PostMapping("/signOut")
    @ResponseBody
    public ResponseEntity<?> signOutUser( HttpServletResponse response) {
        Cookie authCookie = new Cookie("posAuthCookie", null);
        authCookie.setHttpOnly(true);
        authCookie.setPath("/api");
        response.addCookie(authCookie);
        return ResponseEntity.ok("SignOut Successful");
    }

//    @PostMapping("/addItems")
//    @ResponseBody
//    public ResponseEntity<?> addItems(@Valid @RequestBody Item item, HttpServletResponse response) {
//        itemRepository.save(item);
//        return ResponseEntity.ok("SignOut Successful");
//
//    }
}

package com.training.posproject.controllers;

import com.training.posproject.security.message.request.LoginForm;
import com.training.posproject.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600,allowCredentials="true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private AuthService authService;

    @Autowired
    public void setAuthService(AuthService authService){
        this.authService=authService;
    }


    @PostMapping("/signIn")
    @ResponseBody
    public ResponseEntity authenticateUser(@Valid @RequestBody LoginForm loginRequest, HttpServletResponse responseObject) {


        try {
            String token= authService.authenticateUser(loginRequest);

                Cookie authCookie = new Cookie("posAuthCookie", token);
                authCookie.setHttpOnly(true);
                authCookie.setPath("/api");
                authCookie.setMaxAge(2147483647);
                responseObject.addCookie(authCookie);
                return ResponseEntity.ok("Authentication Successful");


        }
        catch (Exception e){
            return ResponseEntity.status(500).build();
        }

    }


    @PostMapping("/signOut")
    @ResponseBody
    public ResponseEntity signOutUser( HttpServletResponse response) {
        Cookie authCookie = new Cookie("posAuthCookie", null);
        authCookie.setHttpOnly(true);
        authCookie.setPath("/api");
        response.addCookie(authCookie);
        return ResponseEntity.ok("SignOut Successful");
    }

}

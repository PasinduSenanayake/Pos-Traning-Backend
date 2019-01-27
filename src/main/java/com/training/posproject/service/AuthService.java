package com.training.posproject.service;

import com.training.posproject.security.message.request.LoginForm;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    String authenticateUser(LoginForm loginRequest);
}

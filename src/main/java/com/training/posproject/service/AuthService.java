package com.training.posproject.service;


import com.training.posproject.security.message.request.LoginForm;
import com.training.posproject.util.Pair;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    Pair<Boolean,?> authenticateUser(LoginForm loginRequest);
}

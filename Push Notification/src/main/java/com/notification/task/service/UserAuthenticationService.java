package com.notification.task.service;

import com.notification.task.exception.ApplicationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public interface UserAuthenticationService {

    UsernamePasswordAuthenticationToken loginWithEmail(String email, String password) throws ApplicationException;
}

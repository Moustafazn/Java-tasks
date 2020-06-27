package com.notification.task.service.impl;

import com.notification.task.dao.UserRepository;
import com.notification.task.exception.ApplicationException;
import com.notification.task.exception.ErrorCode;
import com.notification.task.model.User;
import com.notification.task.service.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;

@Service
@Transactional
public class DefaultUserAuthenticationService implements UserAuthenticationService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UsernamePasswordAuthenticationToken loginWithEmail(String email, String password) throws ApplicationException {
        if (email == null || email.trim().isEmpty()) {
            throw new ApplicationException(ErrorCode.AUTHENTICATION_ERROR);
        }
        if (password == null || password.trim().isEmpty()) {
            throw new ApplicationException(ErrorCode.AUTHENTICATION_ERROR);
        }
        User user = fetchUserFromDb(email, password);
        if (user == null) {
            throw new ApplicationException(ErrorCode.AUTHENTICATION_ERROR);
        }

        return new UsernamePasswordAuthenticationToken(user.getUsername(),
                null,
                Collections.singleton((GrantedAuthority) () -> "USER")
        );
    }

    private User fetchUserFromDb(String email, String password){
      return userRepository.findByEmailAndPassword(email, password);
    }
}

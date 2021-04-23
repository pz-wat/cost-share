package com.wat.pz.costshare.service;

import com.wat.pz.costshare.dto.request.LoginRequest;
import com.wat.pz.costshare.dto.request.SignupRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> doAuthenticateUsr(LoginRequest loginRequest);

    ResponseEntity<?> doRegisterUser(SignupRequest signupRequest);
}

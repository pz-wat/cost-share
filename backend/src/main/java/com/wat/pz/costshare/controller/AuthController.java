package com.wat.pz.costshare.controller;

import com.wat.pz.costshare.dto.request.LoginRequest;
import com.wat.pz.costshare.dto.request.SignupRequest;
import com.wat.pz.costshare.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody @Valid LoginRequest loginRequest) {
        return userService.doAuthenticateUsr(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody @Valid SignupRequest signupRequest) {
        return userService.doRegisterUser(signupRequest);
    }

}

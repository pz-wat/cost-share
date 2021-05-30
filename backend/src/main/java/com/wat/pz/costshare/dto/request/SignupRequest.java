package com.wat.pz.costshare.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SignupRequest {

    @NotBlank(message = "Username cannot be blank!")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @NotBlank(message = "Email cannot be blank!")
    @Size(max = 50, message = "Email must be max 50 characters")
    @Email(message = "Must be an email")
    private String email;

    private Set<String> role;

    @NotBlank(message = "Password cannot be blank!")
    @Size(min = 6, max = 40, message = "Password must be between 6 and 40 characters")
    private String password;

}

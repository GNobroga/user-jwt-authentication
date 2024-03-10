package com.apiauth.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RequestUserDTO {
    
    @NotBlank(message = "user.name.required")
    private String name;

    @NotBlank(message = "user.username.required")
    private String username;

    @Email(message = "user.email.invalid")
    @NotBlank(message = "user.email.required")
    private String email;

    @NotBlank(message = "user.password.required")
    private String password;
}
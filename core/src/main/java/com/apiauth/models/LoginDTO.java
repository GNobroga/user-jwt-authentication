package com.apiauth.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor 
@NoArgsConstructor
@Getter
@Setter
public class LoginDTO {
    
    @NotBlank(message = "login.username.required")
    private String username;

    @NotBlank(message = "login.password.required")
    private String password;
}

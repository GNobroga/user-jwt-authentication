package com.apiauth.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class RefreshTokenDTO {
    
    @NotBlank(message = "token.refreshToken.required")
    private String refreshToken;

}

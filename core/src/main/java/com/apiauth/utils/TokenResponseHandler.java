package com.apiauth.utils;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@JsonPropertyOrder({"tokenType", "username"})
public class TokenResponseHandler {

    private String accessToken;

    private String tokenType;

    private String refreshToken;

}

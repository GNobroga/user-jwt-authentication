package com.apiauth.models;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseUserDTO {
    private UUID id;
    private String name;
    private String email;
    private String username;
    private List<String> roles;
}
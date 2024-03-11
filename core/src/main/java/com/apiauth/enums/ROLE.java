package com.apiauth.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ROLE {
    COMMON ("COMMON"), ADMIN ("ADMIN");

    private final String value;
}

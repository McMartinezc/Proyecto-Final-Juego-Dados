package com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class LoginRequest {
    private String username;
    private String password;

    public LoginRequest() {
    }
}

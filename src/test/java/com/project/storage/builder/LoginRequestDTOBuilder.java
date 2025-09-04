package com.project.storage.builder;

import com.project.storage.dto.login.LoginRequestDTO;

public class LoginRequestDTOBuilder {
    private String username = "testUser";
    private String password = "testPassword123";

    public LoginRequestDTOBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public LoginRequestDTOBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public LoginRequestDTO build() {
        return new LoginRequestDTO(username, password);
    }
}


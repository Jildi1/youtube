package com.example.Youtube.Model;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private String username;
    private String password;
    private String email;

    public AuthenticationRequest(){
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

package com.example.zomfit.models.login;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @SerializedName("email")
    public String email;

    @SerializedName("password")
    public String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

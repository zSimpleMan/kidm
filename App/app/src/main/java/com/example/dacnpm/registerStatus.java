package com.example.dacnpm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class registerStatus {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("email")
    @Expose
    private String email;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

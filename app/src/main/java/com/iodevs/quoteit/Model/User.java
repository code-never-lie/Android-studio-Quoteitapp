package com.iodevs.quoteit.Model;

/**
 * Created by Touseef Rao on 9/12/2018.
 */

public class User {
    private String name,email,userId;

    public User(){}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public User(String name, String email, String userId) {
        this.name = name;
        this.email = email;
        this.userId = userId;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

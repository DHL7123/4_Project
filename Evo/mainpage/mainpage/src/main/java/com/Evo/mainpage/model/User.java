package com.Evo.mainpage.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String userId;
    private String userName;

    // 생성자
    public User() {}

    public User(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
}
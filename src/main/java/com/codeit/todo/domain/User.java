package com.codeit.todo.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private int userId;

    @Column(name= "name", nullable = false)
    private String name;


    @Column(name= "email", nullable = false)
    private String email;

    @Column(name= "password", nullable = false)
    private String password;

    @Column(name = "profile_pic", nullable = false)
    private String profilePic;


    @Builder
    public User(int userId, String name, String email, String password, String profilePic) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.profilePic = profilePic;
    }

    public void updateProfilePic(String completePicUrl){
        this.profilePic = completePicUrl;
    }
}

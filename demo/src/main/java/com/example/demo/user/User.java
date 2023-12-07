package com.example.demo.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 45, nullable = false)
    private String username;

    @Column(length = 100, nullable = false)
    private String password;

    @Convert(converter = StringArrayConverter.class)
    private List<String> roles = new ArrayList<>();

    @Builder
    public User(Long id, String email, String password, String username, List<String> roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.roles = roles;
    }

}

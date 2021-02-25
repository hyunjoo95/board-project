package com.example.myhome.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String useremail;
    private String username;
    private String password;
    private Boolean enabled;


    @ManyToMany
    @JoinTable(
        name = "user_role", //조인테이블명
        joinColumns = @JoinColumn(name = "user_id"), //외래키
        inverseJoinColumns = @JoinColumn(name = "role_id") //반대 엔티티의 외래키
        )


    private List<Role> roles = new ArrayList<>();



}

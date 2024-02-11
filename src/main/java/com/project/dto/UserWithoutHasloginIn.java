package com.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserWithoutHasloginIn {
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private int role;
    
    public UserWithoutHasloginIn() {}
}

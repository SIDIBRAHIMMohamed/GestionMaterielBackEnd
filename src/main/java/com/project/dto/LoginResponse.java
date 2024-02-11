package com.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
	private Long id;
    private String nom;
    private String prenom;
    private String email;
    private int role;
    private boolean haslogiIn;
    public LoginResponse() {}
}


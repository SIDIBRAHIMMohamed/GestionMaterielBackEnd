package com.project.dto;


import lombok.Data;



@Data
public class LoginResponse {

    private String nom;
    private String prenom;
    private String email;
    private int role;
    private boolean haslogiIn;
    public LoginResponse() {}
	public LoginResponse(String nom, String prenom, String email, int role, boolean haslogiIn) {
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.role = role;
		this.haslogiIn = haslogiIn;
	}
    
}


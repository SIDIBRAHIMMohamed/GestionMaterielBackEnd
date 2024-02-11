package com.project.entities;

import jakarta.persistence.Column;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Le nom ne peut pas être nul")
    @Size(min = 2, message = "Le nom doit contenir au moins 2 caractères")
    @Column(unique = true)
    private String nom;

    @NotNull(message = "Le prénom ne peut pas être nul")
    @Size(min = 2, message = "Le prénom doit contenir au moins 2 caractères")
    private String prenom;
    
    @Column(unique = true)
    @NotNull()
    @Email(message = "L'email doit être valide")
    private String email;
    
    @Column(name = "has_login_in")
    private boolean hasloginIn;
    
    @NotNull (message = "Le mot de passe doit pas est null")
    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    private String password;

    private int role; // 1 pour administrateur, 0 pour utilisateur

    // Constructeur sans arguments nécessaire pour JPA
    public Utilisateur() {}

    // Constructeur complet pour faciliter la création d'instances
    public Utilisateur(String nom, String prenom, String email, String password, int role, boolean hasloginIn) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.role = role;
        this.hasloginIn=hasloginIn;
    }

   
}

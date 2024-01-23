package com.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entities.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

}

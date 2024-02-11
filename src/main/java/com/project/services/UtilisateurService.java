package com.project.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.project.entities.Utilisateur;
import com.project.repositories.UtilisateurRepository;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
@Service
public class UtilisateurService {
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public Utilisateur creerUtilisateur(Utilisateur utilisateur) {
    	String passwordcoder = passwordEncoder.encode(utilisateur.getPassword());
    	utilisateur.setPassword(passwordcoder);
        return utilisateurRepository.save(utilisateur);
    }


    public List<Utilisateur> listerUtilisateurs() {
        return utilisateurRepository.findAll();
    }
    
    
    public Optional<Utilisateur> obtenirUtilisateurParId(Long id) {
        return utilisateurRepository.findById(id);
    }
    public Long CountUsers() {
    	return utilisateurRepository.count();
    }

    
    public Utilisateur modifierUtilisateur(Long id, Utilisateur utilisateurDetails) {
        Optional<Utilisateur> utilisateurExistantOptional = utilisateurRepository.findById(id);

        if (utilisateurExistantOptional.isPresent()) {
            Utilisateur utilisateurExistant = utilisateurExistantOptional.get();

            // Mise à jour des champs
            utilisateurExistant.setNom(utilisateurDetails.getNom());
            utilisateurExistant.setPrenom(utilisateurDetails.getPrenom());
            utilisateurExistant.setEmail(utilisateurDetails.getEmail());

            // Encoder le nouveau mot de passe si présent
            if (utilisateurDetails.getPassword() != null) {
                String passwordEncoded = passwordEncoder.encode(utilisateurDetails.getPassword());
                utilisateurExistant.setPassword(passwordEncoded);
            }

            utilisateurExistant.setRole(utilisateurDetails.getRole());

            // Enregistrer les modifications
            return utilisateurRepository.save(utilisateurExistant);
        } else {
            return null;
        }
    }
    
    
    public void supprimerUtilisateur(Long id) {
        utilisateurRepository.deleteById(id);
    }
    public Utilisateur login(String email, String password) {
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findByEmail(email);
        if (utilisateurOptional.isPresent() && passwordEncoder.matches(password, utilisateurOptional.get().getPassword())) {
            return utilisateurOptional.get();
        }
        throw new EntityNotFoundException("Informations de connexion incorrectes");
    }
    
    
    public Page<Utilisateur> getUtilisateursPaginated(Pageable pageable) {
        return utilisateurRepository.findAll(pageable);
    }
    
    





    
    
}


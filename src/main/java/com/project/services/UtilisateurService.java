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
            utilisateurExistant.setPassword(utilisateurDetails.getPassword());  
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
        Optional<Utilisateur> utilisateur = utilisateurRepository.findByEmail(email);
        if (utilisateur.isPresent() && utilisateur.get().getPassword().equals(password)) { //  comparer les hash des mots de passe dans une vraie application
            return utilisateur.get();
        }
        throw new EntityNotFoundException("Informations de connexion incorrectes");
    }
    
    
    public Page<Utilisateur> getUtilisateursPaginated(Pageable pageable) {
        return utilisateurRepository.findAll(pageable);
    }
    /*
    @PostConstruct
    public void initAdminUser() {
        Optional<Utilisateur> adminUser = utilisateurRepository.findByEmail("admin@example.com");
        if (adminUser.isEmpty()) {
            Utilisateur admin = new Utilisateur("Admin", "Admin", "admin@example.com", "password", 1);
            // On peux hasher le mot de passe
            utilisateurRepository.save(admin);
        }
    }
*/
    





    
    
}


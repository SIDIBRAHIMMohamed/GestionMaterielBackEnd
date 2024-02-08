package com.project.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.project.entities.Utilisateur;
import com.project.repositories.UtilisateurRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class UtilisateurService {
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    
    
    
    public Utilisateur creerUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }


    public List<Utilisateur> listerUtilisateurs() {
        return utilisateurRepository.findAll();
    }
    
    
    public Optional<Utilisateur> obtenirUtilisateurParId(Long id) {
        return utilisateurRepository.findById(id);
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

    





    
    
}


package com.project.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.project.entities.Utilisateur;
import com.project.repositories.UtilisateurRepository;

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
            utilisateurExistant.setPassowrd(utilisateurDetails.getPassowrd());  // Vérifiez l'orthographe correcte
            utilisateurExistant.setRole(utilisateurDetails.getRole());

            // Enregistrer les modifications
            return utilisateurRepository.save(utilisateurExistant);
        } else {
            // Gérer le cas où l'utilisateur n'existe pas
            // Par exemple, retourner null ou lever une exception personnalisée
            return null;
        }
    }
    
    
    public void supprimerUtilisateur(Long id) {
        utilisateurRepository.deleteById(id);
    }





    
    
}


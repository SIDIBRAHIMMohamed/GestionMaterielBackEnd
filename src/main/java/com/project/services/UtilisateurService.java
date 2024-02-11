package com.project.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dto.LoginResponse;
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
    
    
    public void changerMotDePasseEtActiverLogin(String email, String nouveauMotDePasse) {
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findByEmail(email);
        if (utilisateurOptional.isPresent()) {
            Utilisateur utilisateur = utilisateurOptional.get();
            // Encoder le nouveau mot de passe
            String motDePasseEncode = passwordEncoder.encode(nouveauMotDePasse);
            utilisateur.setPassword(motDePasseEncode);
            utilisateur.setHasloginIn(true); // Activer hasloginIn
            utilisateurRepository.save(utilisateur);
        } else {
            // Gérer le cas où l'utilisateur n'est pas trouvé
            throw new RuntimeException("Utilisateur introuvable avec l'email : " + email);
        }
    }
    
    public void resetPassword(String email) {
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findByEmail(email);
        if (utilisateurOptional.isPresent()) {
            Utilisateur utilisateur = utilisateurOptional.get();
            // Réinitialiser le mot de passe à "123456"
            String motDePasseEncode = passwordEncoder.encode("123456");
            utilisateur.setPassword(motDePasseEncode);
            utilisateurRepository.save(utilisateur);
        } else {
            // Gérer le cas où l'utilisateur n'est pas trouvé
            throw new RuntimeException("Utilisateur introuvable avec l'email : " + email);
        }
    }
    
    public LoginResponse mapToLoginResponse(Utilisateur utilisateur) {
        LoginResponse response = new LoginResponse();
        response.setId(utilisateur.getId());
        response.setNom(utilisateur.getNom());
        response.setPrenom(utilisateur.getPrenom());
        response.setEmail(utilisateur.getEmail());
        response.setRole(utilisateur.getRole());
        response.setHaslogiIn(utilisateur.isHasloginIn());
        
        return response;
    }




    
    
}


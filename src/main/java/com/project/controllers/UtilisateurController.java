package com.project.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.dto.LoginRequest;
import com.project.entities.Utilisateur;
import com.project.services.UtilisateurService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class UtilisateurController {
    @Autowired
    private UtilisateurService utilisateurservice;
    

    
    
    @GetMapping("/utilisateurs")
    public ResponseEntity<List<Utilisateur>> getAllUtilisateurs() {
        try {
            List<Utilisateur> utilisateurs = utilisateurservice.listerUtilisateurs();
            

            if (utilisateurs.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(utilisateurs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/utilisateurs/{id}")
    public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable("id") long id) {
        try {
            Optional<Utilisateur> utilisateurOptional = utilisateurservice.obtenirUtilisateurParId(id);
            if (utilisateurOptional.isPresent()) {
                Utilisateur utilisateur = utilisateurOptional.get(); // Convertit Optional<Utilisateur> en Utilisateur
                return new ResponseEntity<>(utilisateur, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PostMapping("/utilisateurs")
    public ResponseEntity<Utilisateur> createUtilisateur(@RequestBody Utilisateur utilisateur) {
        try {
            Utilisateur nouvelUtilisateur = utilisateurservice.creerUtilisateur(utilisateur);
            return new ResponseEntity<>(nouvelUtilisateur, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    @PutMapping("/utilisateurs/{id}")
    public ResponseEntity<Utilisateur> updateUtilisateur(@PathVariable("id") long id, @Valid @RequestBody Utilisateur utilisateurDetails) {
        try {
            Utilisateur utilisateurMisAJour = utilisateurservice.modifierUtilisateur(id, utilisateurDetails);
            if (utilisateurMisAJour != null) {
                return new ResponseEntity<>(utilisateurMisAJour, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    
    @DeleteMapping("/utilisateurs/{id}")
    public ResponseEntity<HttpStatus> supprimerUtilisateur(@PathVariable("id") long id) {
        try {
            utilisateurservice.supprimerUtilisateur(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Utilisateur utilisateur = utilisateurservice.login(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok(utilisateur); // On  pout retourner un objet JWT 
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //http://localhost:9090/api/utilisateurs/paginated?page=0&size=8  :premier page avec 8 utilisateurs
    @GetMapping("/utilisateurs/paginated")
    public ResponseEntity<List<Utilisateur>> getUtilisateursPaginated(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        try {
            Pageable paging = PageRequest.of(page, size);
            Page<Utilisateur> pageUtilisateurs = utilisateurservice.getUtilisateursPaginated(paging);
            List<Utilisateur> utilisateurs = pageUtilisateurs.getContent();

            if (utilisateurs.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(utilisateurs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





    
}

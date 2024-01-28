package com.project.controllers;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;



import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.entities.Utilisateur;
import com.project.services.UtilisateurService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(UtilisateurController.class)
public class UtilisateurControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UtilisateurService utilisateurService;

    @Test
    public void getAllUtilisateurs_ShouldReturnUtilisateurs() throws Exception {
        Utilisateur user1 = new Utilisateur("Mohamed", "SID BRAHIM", "mohamed@gmail.com", "password", 0);
        Utilisateur user2 = new Utilisateur("tah", "DIDI", "tah@gmail.com", "password", 0);

        List<Utilisateur> allUsers = Arrays.asList(user1, user2);

        when(utilisateurService.listerUtilisateurs()).thenReturn(allUsers);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/utilisateurs")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nom", is(user1.getNom())))
                .andExpect(jsonPath("$[1].nom", is(user2.getNom())));
    }
    
    
    @Test
    public void getUtilisateurById_WhenFound_ShouldReturnUtilisateur() throws Exception {
        Utilisateur user = new Utilisateur("Mohamed", "SID BRAHIM", "mohamed@gmail.com", "password", 0);
        user.setId(1L);

        when(utilisateurService.obtenirUtilisateurParId(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/utilisateurs/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom", is(user.getNom())));
    }
    
    
    @Test
    public void createUtilisateur_ShouldReturnCreatedUtilisateur() throws Exception {
        Utilisateur newUser = new Utilisateur("Mohamed", "SID BRAHIM", "mohamed@gmail.com", "password", 0);

        // Simuler le comportement de creerUtilisateur pour accepter n'importe quel objet Utilisateur
        when(utilisateurService.creerUtilisateur(any(Utilisateur.class))).thenReturn(newUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/utilisateurs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(newUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nom", is(newUser.getNom())));
    }
    
    @Test
    public void updateUtilisateur_WhenFound_ShouldReturnUpdatedUtilisateur() throws Exception {
        Utilisateur updatedUser = new Utilisateur("Mohamed", "SID BRAHIM", "Mohamed@gmail.com", "newpassword", 0);
        updatedUser.setId(1L);

        when(utilisateurService.modifierUtilisateur(eq(1L), any(Utilisateur.class))).thenReturn(updatedUser);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/utilisateurs/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.password", is(updatedUser.getPassword())));
    }
    
    
    @Test
    public void supprimerUtilisateur_WhenFound_ShouldReturnNoContent() throws Exception {
        doNothing().when(utilisateurService).supprimerUtilisateur(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/utilisateurs/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }





}

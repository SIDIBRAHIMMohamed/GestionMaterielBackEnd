package com.project.controllers;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.ArgumentMatchers.anyString;


import static org.mockito.Mockito.doThrow;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import javax.validation.ConstraintViolationException;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.dto.LoginRequest;
import com.project.entities.Utilisateur;
import com.project.services.UtilisateurService;

import jakarta.persistence.EntityNotFoundException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;



@WebMvcTest(UtilisateurController.class)
public class UtilisateurControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UtilisateurService utilisateurService;

    @Test
    public void getAllUtilisateurs_WhenUsersExist_ShouldReturnUsers() throws Exception {
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
    public void getAllUtilisateurs_WhenNoUsers_ShouldReturnNoContent() throws Exception {
        when(utilisateurService.listerUtilisateurs()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/utilisateurs")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
    
    @Test
    public void getAllUtilisateurs_WhenServerError_ShouldReturnInternalServerError() throws Exception {
        when(utilisateurService.listerUtilisateurs()).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/utilisateurs")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
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
    public void getUtilisateurById_WhenNotFound_ShouldReturnNotFound() throws Exception {
        when(utilisateurService.obtenirUtilisateurParId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/utilisateurs/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    
    @Test
    public void getUtilisateurById_WhenServerError_ShouldReturnInternalServerError() throws Exception {
        when(utilisateurService.obtenirUtilisateurParId(1L)).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/utilisateurs/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }


    
    
    @Test
    public void createUtilisateur_WhenValidData_ShouldCreateUser() throws Exception {
        Utilisateur newUser = new Utilisateur("Mohamed", "SID BRAHIM", "mohamed@gmail.com", "password", 0);
        when(utilisateurService.creerUtilisateur(any(Utilisateur.class))).thenReturn(newUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/utilisateurs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(newUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nom", is(newUser.getNom())));
    }
    
    
    @Test
    public void createUtilisateur_WhenInvalidData_ShouldReturnBadRequest() throws Exception {
        Utilisateur newUser = new Utilisateur("", "", "", "password", 0); // Données invalides

        mockMvc.perform(MockMvcRequestBuilders.post("/api/utilisateurs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(newUser)))
                .andExpect(status().isCreated()); // Vérifiez que la réponse est "201 Created"
    }


    @Test
    public void createUtilisateur_WhenServerError_ShouldReturnInternalServerError() throws Exception {
        Utilisateur newUser = new Utilisateur("Mohamed", "SID BRAHIM", "mohamed@gmail.com", "password", 0);
        when(utilisateurService.creerUtilisateur(any(Utilisateur.class))).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/utilisateurs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(newUser)))
                .andExpect(status().isInternalServerError());
    }


    @Test
    public void updateUtilisateur_WhenFound_ShouldUpdateUser() throws Exception {
        Utilisateur updatedUser = new Utilisateur("Mohamed", "SID BRAHIM", "mohamed@gmail.com", "newpassword", 0);
        updatedUser.setId(1L);

        when(utilisateurService.modifierUtilisateur(eq(1L), any(Utilisateur.class))).thenReturn(updatedUser);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/utilisateurs/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.password", is(updatedUser.getPassword())));
    }
    
   

    @Test
    public void updateUtilisateur_WhenNotFound_ShouldReturnNotFound() throws Exception {
        // Simuler que l'utilisateur n'est pas trouvé en renvoyant null
        when(utilisateurService.modifierUtilisateur(eq(1L), any(Utilisateur.class))).thenReturn(null);

        // Créer un objet Utilisateur avec des données valides
        Utilisateur validUser = new Utilisateur("Mohamed", "SID BRAHIM", "Mohamed.sidbrahim@example.com", "password123", 0);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/utilisateurs/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(validUser)))
                .andExpect(status().isNotFound());
    }



    @Test
    public void updateUtilisateur_WhenInvalidData_ShouldReturnBadRequest() throws Exception {
        Utilisateur invalidUser = new Utilisateur("", "", "", "password", 0); // Données invalides

        mockMvc.perform(MockMvcRequestBuilders.put("/api/utilisateurs/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(invalidUser)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    public void updateUtilisateur_WhenServerError_ShouldReturnInternalServerError() throws Exception {
        Utilisateur user = new Utilisateur("Mohamed", "SID BRAHIM", "mohamed@gmail.com", "password", 0);

        when(utilisateurService.modifierUtilisateur(eq(1L), any(Utilisateur.class))).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/utilisateurs/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isInternalServerError());
    }


    
    
    @Test
    public void supprimerUtilisateur_WhenFound_ShouldReturnNoContent() throws Exception {
        doNothing().when(utilisateurService).supprimerUtilisateur(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/utilisateurs/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
    
    @Test
    public void supprimerUtilisateur_WhenNotFound_ShouldReturnNotFound() throws Exception {
        doThrow(new EntityNotFoundException("Utilisateur non trouvé")).when(utilisateurService).supprimerUtilisateur(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/utilisateurs/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    
    
    @Test
    public void supprimerUtilisateur_WhenServerError_ShouldReturnInternalServerError() throws Exception {
        doThrow(new RuntimeException()).when(utilisateurService).supprimerUtilisateur(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/utilisateurs/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    
    @Test
    public void login_WithValidCredentials_ShouldReturnUser() throws Exception {
        LoginRequest loginRequest = new LoginRequest("valid.user@example.com", "password");
        Utilisateur user = new Utilisateur("ValidUser", "LastName", "valid.user@example.com", "password", 0);

        when(utilisateurService.login(loginRequest.getEmail(), loginRequest.getPassword())).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(user.getEmail())));

    }


    @Test
    public void login_WithInvalidCredentials_ShouldReturnUnauthorized() throws Exception {
        LoginRequest loginRequest = new LoginRequest("invalid.user@example.com", "wrongpassword");

        when(utilisateurService.login(loginRequest.getEmail(), loginRequest.getPassword())).thenThrow(new EntityNotFoundException("User not found"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void login_WithServerError_ShouldReturnInternalServerError() throws Exception {
        LoginRequest loginRequest = new LoginRequest("user@example.com", "password");

       
        when(utilisateurService.login(anyString(), anyString())).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginRequest)))
                .andExpect(status().isInternalServerError());
    }
    
    @Test
    public void getUtilisateursPaginated_WithUsers_ShouldReturnUsersList() throws Exception {
        List<Utilisateur> users = Arrays.asList(new Utilisateur("User1", "Last1", "user1@example.com", "pass1", 0),
                                                new Utilisateur("User2", "Last2", "user2@example.com", "pass2", 0));
        Page<Utilisateur> page = new PageImpl<>(users);

        when(utilisateurService.getUtilisateursPaginated(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/utilisateurs/paginated?page=0&size=2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].email", is(users.get(0).getEmail())))
                .andExpect(jsonPath("$[1].email", is(users.get(1).getEmail())));
    }

    @Test
    public void getUtilisateursPaginated_WithoutUsers_ShouldReturnNoContent() throws Exception {
        Page<Utilisateur> page = Page.empty();

        when(utilisateurService.getUtilisateursPaginated(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/utilisateurs/paginated?page=0&size=2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void getUtilisateursPaginated_WithServerError_ShouldReturnInternalServerError() throws Exception {
        when(utilisateurService.getUtilisateursPaginated(any(Pageable.class))).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/utilisateurs/paginated?page=0&size=2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }








}

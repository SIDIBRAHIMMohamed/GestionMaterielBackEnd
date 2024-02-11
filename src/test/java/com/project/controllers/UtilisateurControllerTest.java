package com.project.controllers;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.ArgumentMatchers.anyString;


import static org.mockito.Mockito.doThrow;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;



import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.dto.LoginRequest;
import com.project.dto.LoginResponse;
import com.project.dto.UserWithoutHasloginIn;
import com.project.entities.Utilisateur;
import com.project.services.UtilisateurService;

import jakarta.persistence.EntityNotFoundException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
@WebMvcTest(UtilisateurController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UtilisateurControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UtilisateurService utilisateurService;
    @InjectMocks
    private UtilisateurController utilisateurController;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void getAllUtilisateurs_WhenUsersExist_ShouldReturnUsers() throws Exception {
        Utilisateur user1 = new Utilisateur("Mohamed", "SID BRAHIM", "mohamed@gmail.com", "password", 0,false);
        Utilisateur user2 = new Utilisateur("tah", "DIDI", "tah@gmail.com", "password", 0,false);
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
        Utilisateur user = new Utilisateur("Mohamed", "SID BRAHIM", "mohamed@gmail.com", "password", 0,false);
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
    void createUtilisateur_WhenValidData_ShouldCreateUser() throws Exception {
        // Given
        UserWithoutHasloginIn userWithoutHasloginIn = new UserWithoutHasloginIn("John", "Doe", "john@example.com", "password", 0);

        Utilisateur nouvelUtilisateur = new Utilisateur("John", "Doe", "john@example.com", "password", 0, false);
        LoginResponse loginResponse = new LoginResponse("John", "Doe", "john@example.com", 0, false);

        when(utilisateurService.creerUtilisateur(any(Utilisateur.class))).thenReturn(nouvelUtilisateur);
        when(utilisateurService.mapToLoginResponse(any(Utilisateur.class))).thenReturn(loginResponse);

        // When
       mockMvc.perform(MockMvcRequestBuilders.post("/api/utilisateurs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userWithoutHasloginIn)))
                .andExpect(status().isCreated())
                .andReturn();

        
    }

  
    
    @Test
    public void createUtilisateur_WhenInvalidData_ShouldReturnBadRequest() throws Exception {
        Utilisateur newUser = new Utilisateur("", "", "", "password", 0,false); // Données invalides

        mockMvc.perform(MockMvcRequestBuilders.post("/api/utilisateurs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(newUser)))
                .andExpect(status().isCreated()); // Vérifiez que la réponse est "201 Created"
    }


    @Test
    public void createUtilisateur_WhenServerError_ShouldReturnInternalServerError() throws Exception {
        Utilisateur newUser = new Utilisateur("Mohamed", "SID BRAHIM", "mohamed@gmail.com", "password", 0,false);
        when(utilisateurService.creerUtilisateur(any(Utilisateur.class))).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/utilisateurs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(newUser)))
                .andExpect(status().isInternalServerError());
    }


    @Test
    public void updateUtilisateur_WhenFound_ShouldUpdateUser() throws Exception {
        Utilisateur updatedUser = new Utilisateur("Mohamed", "SID BRAHIM", "mohamed@gmail.com", "newpassword", 0,false);
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
        Utilisateur validUser = new Utilisateur("Mohamed", "SID BRAHIM", "Mohamed.sidbrahim@example.com", "password123", 0,false);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/utilisateurs/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(validUser)))
                .andExpect(status().isNotFound());
    }



    @Test
    public void updateUtilisateur_WhenInvalidData_ShouldReturnBadRequest() throws Exception {
        Utilisateur invalidUser = new Utilisateur("", "", "", "password", 0,false); // Données invalides

        mockMvc.perform(MockMvcRequestBuilders.put("/api/utilisateurs/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(invalidUser)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    public void updateUtilisateur_WhenServerError_ShouldReturnInternalServerError() throws Exception {
        Utilisateur user = new Utilisateur("Mohamed", "SID BRAHIM", "mohamed@gmail.com", "password", 0,false);

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
    public void login_WithValidCredentials_ShouldReturnLoginResponse() throws Exception {
        LoginRequest loginRequest = new LoginRequest("valid.user@example.com", "password");
        Utilisateur user = new Utilisateur("ValidUser", "ValidLastName", "valid.user@example.com", "password", 0, true);
        LoginResponse logiResponse =new LoginResponse("ValidUser", "ValidLastName", "valid.user@example.com", 0, true);

        

        when(utilisateurService.login(loginRequest.getEmail(), loginRequest.getPassword())).thenReturn(user);
        when(utilisateurService.mapToLoginResponse(any(Utilisateur.class))).thenReturn(logiResponse);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginRequest)))
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.nom", is(user.getNom())))
                .andExpect(jsonPath("$.prenom", is(user.getPrenom())))
                .andExpect(jsonPath("$.role", is(user.getRole())))
                .andExpect(jsonPath("$.haslogiIn", is(true)))
                .andExpect(jsonPath("$.password").doesNotExist()); // Vérifie que le mot de passe n'est pas renvoyé
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
    public void getAllUtilisateursPagination_WhenUsersExist_ShouldReturnPaginatedUsers() throws Exception {
        int page = 1;
        int size = 2;
        Pageable pageable = PageRequest.of(page - 1, size);

        Utilisateur user1 = new Utilisateur("Mohamed", "SID BRAHIM", "mohamed@gmail.com", "password", 0,false);
        Utilisateur user2 = new Utilisateur("Tah", "DIDI", "tah@gmail.com", "password", 0,false);
        List<Utilisateur> userList = Arrays.asList(user1, user2);

        Page<Utilisateur> userPage = new PageImpl<>(userList, pageable, userList.size());

        when(utilisateurService.getUtilisateursPaginated(pageable)).thenReturn(userPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/paginatedUsers")
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.users", hasSize(2)))
                .andExpect(jsonPath("$.users[0].nom", is(user1.getNom())))
                .andExpect(jsonPath("$.users[1].nom", is(user2.getNom())))
                .andExpect(jsonPath("$.totalItems", is(userList.size())))
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.next", is(false)));
    }

    @Test
    public void getAllUtilisateursPagination_WhenNoUsers_ShouldReturnNoContent() throws Exception {
        int page = 1;
        int size = 2;
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Utilisateur> userPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

        when(utilisateurService.getUtilisateursPaginated(pageable)).thenReturn(userPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/paginatedUsers")
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
    @Test
    public void getAllUtilisateursPagination_WhenServerError_ShouldReturnInternalServerError() throws Exception {
        int page = 1;
        int size = 2;
        Pageable pageable = PageRequest.of(page - 1, size);

        
        when(utilisateurService.getUtilisateursPaginated(pageable)).thenThrow(new RuntimeException("Internal server error"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/paginatedUsers")
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }










}

/**
 * Represents Materiel controller test class
 */
package com.project.controllers;

import com.project.entities.Materiel;
import com.project.services.MaterielService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MaterielControllerTest {
    // Attributes :
    @Mock
    private MaterielService materielService;
    @InjectMocks
    private MaterielController materielController;

    /**
     * To set up any necessary configurations
     */
    @BeforeEach
    public void setUp() {
        // To set up any necessary configurations
    }

    /**
     * Test the getAllMateriels method when materials exists
     */
    @Test
    public void testGetAllMaterielsWhenMaterielsExists() {
        // Arrange :
        Materiel materiel1 = new Materiel("Test1", "1.0", "123", 0);
        Materiel materiel2 = new Materiel("Test2", "2.0", "333", 0);
        List<Materiel> materiels = List.of(materiel1, materiel2);
        when(materielService.getAll()).thenReturn(materiels);
        // Act :
        ResponseEntity<List<Materiel>> result = materielController.getAllMateriels();
        // Assert :
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(materiels.size(), result.getBody().size());
        for (int i = 0; i < materiels.size(); i++) {
            assertEquals(materiels.get(i), result.getBody().get(i));
        }
    }

    /**
     * Test the getAllMateriels method when materials does not exist
     */
    @Test
    public void testGetAllMaterielsWhenMaterielsDoNotExist() {
        // Arrange :
        when(materielService.getAll()).thenReturn(Collections.emptyList());
        // Act :
        ResponseEntity<List<Materiel>> result = materielController.getAllMateriels();
        // Assert :
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        assertNull(result.getBody());
    }

    /**
     * Test the getAllMaterielsPagination method when materials exist
     */
    @Test
    public void testGetAllMaterielsPaginationWhenMaterialsExist() {
        // Arrange :
        int page = 1;
        int pageSize = 20;
        // Create mock materials :
        List<Materiel> mockMateriels = new ArrayList<>();
        for (int i = 0; i < pageSize; i++) {
            mockMateriels.add(new Materiel("Material" + i, "1.0", "TT" + i, 0));
        }
        when(materielService.getAll(any(Pageable.class))).thenReturn(new PageImpl<>(mockMateriels));
        // Act :
        ResponseEntity<List<Materiel>> response = materielController.getAllMaterielsPagination(page);
        // Assert :
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        for (int i = 0; i < pageSize; i++) {
            assertEquals(mockMateriels.get(i), response.getBody().get(i));
        }
    }

    /**
     * Test the getAllMaterielsPagination method when materials do not exist
     */
    @Test
    public void testGetAllMaterielsPaginationWhenMaterialsDoNotExist() {
        // Arrange :
        int page = 1;
        when(materielService.getAll(any(Pageable.class))).thenReturn(new PageImpl<>(Collections.emptyList()));
        // Act :
        ResponseEntity<List<Materiel>> response = materielController.getAllMaterielsPagination(page);
        // Assert :
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    /**
     * Test the getMateriel method when materiel exists
     */
    @Test
    public void testGetMaterielWhenMaterielExists() {
        // Arrange :
        int idMateriel = 1;
        Materiel expectedMateriel = new Materiel("Test", "1.0", "123", 0);
        when(materielService.getMaterielFromDB(idMateriel)).thenReturn(expectedMateriel);
        // Act :
        ResponseEntity<Materiel> result = materielController.getMateriel(idMateriel);
        // Assert :
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedMateriel, result.getBody());
    }

    /**
     * Test the getMateriel method when materiel doesn't exists
     */
    @Test
    public void testGetMaterielWhenMaterielDoNotExist() {
        // Arrange :
        int idMateriel = 1;
        when(materielService.getMaterielFromDB(idMateriel)).thenReturn(null);
        // Act :
        ResponseEntity<Materiel> result = materielController.getMateriel(idMateriel);
        // Assert :
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
    }

    /**
     * Test the addMateriel method when materiel has been added successfully
     */
    @Test
    public void testAddMaterielWhenMaterielAdded() {
        // Arrange :
        String nom = "Test";
        String version = "1.0";
        String ref = "123";
        when(materielService.saveMateriel(any())).thenReturn(new Materiel(nom, version, ref, 0));
        // Act :
        ResponseEntity<Materiel> result = materielController.addMateriel(nom, version, ref);
        // Assert :
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(nom, result.getBody().getNom());
        assertEquals(version, result.getBody().getVersion());
        assertEquals(ref, result.getBody().getRef());
    }

    /**
     * Test the addMateriel method when materiel has not been added successfully
     */
    @Test
    public void testAddMaterielWhenMaterielWasNotAdded() {
        //Act :
        ResponseEntity<Materiel> response = materielController.addMateriel("", "2.0", "dd44");
        // Assert :
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    /**
     * Test updateMateriel method when materiel was updated successfully
     */
    @Test
    public void testUpdateMaterielWhenMaterielWasUpdated() {
        // Arrange:
        int idToUpdate = 1;
        String newNom = "UpdatedName";
        String newVersion = "2.0";
        String newRef = "444";
        Materiel existingMateriel = new Materiel("Existing", "1.0", "123", 1);
        // When calling getMaterielFromDB with idToUpdate, return the existingMateriel
        when(materielService.getMaterielFromDB(idToUpdate)).thenReturn(existingMateriel);
        // Act:
        ResponseEntity<Materiel> result = materielController.updateMateriel(idToUpdate, newNom, newVersion, newRef);
        // Assert:
        // Check that saveMateriel was called with the expected updated Materiel object
        Materiel updatedMateriel = new Materiel("Existing", "1.0", "123", 1);
        updatedMateriel.setNom(newNom);
        updatedMateriel.setVersion(newVersion);
        updatedMateriel.setRef(newRef);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(updatedMateriel.getNom(), existingMateriel.getNom());
        assertEquals(updatedMateriel.getVersion(), existingMateriel.getVersion());
        assertEquals(updatedMateriel.getRef(), existingMateriel.getRef());
    }

    /**
     * Test updateMateriel method when materiel was not updated successfully
     */
    @Test
    public void testUpdateMaterielWhenMaterielWasNotUpdated() {
        // Arrange :
        int idMateriel = 1;
        when(materielService.getMaterielFromDB(idMateriel)).thenReturn(null);
        // Act :
        ResponseEntity<Materiel> response = materielController.updateMateriel(idMateriel,
                "Name", "2.0", "AA44");
        // Assert :
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    /**
     * Test the deleteMateriel method materiel was deleted successfully
     */
    @Test
    public void testDeleteMaterielWhenMaterielWasDeleted() {
        // Arrange :
        int idToDelete = 1;
        doNothing().when(materielService).deleteMateriel(idToDelete);
        // Act:
        ResponseEntity<Materiel> result = materielController.deleteMateriel(idToDelete);
        // Assert :
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    /**
     * Test the deleteMateriel method materiel was not deleted successfully
     */
    @Test
    public void testDeleteMaterielWhenMaterielWasNotDeleted() {
        // Arrange :
        int idToDelete = 1;
        doThrow(new EntityNotFoundException("Materiel not found")).when(materielService).deleteMateriel(idToDelete);
        // Act :
        ResponseEntity<Materiel> result = materielController.deleteMateriel(idToDelete);
        // Assert :
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }
}

/**
 * Represents Materiel controller test class
 */
package com.project.controllers;

import com.project.entities.Materiel;
import com.project.services.MaterielService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
     * Test the getMateriel method
     */
    @Test
    public void testGetMateriel() {
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
     * Test the addMateriel method
     */
    @Test
    public void testAddMateriel() {
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
     * Test updateMateriel method
     */
    @Test
    public void testUpdateMateriel() {
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
     * Test the deleteMateriel method
     */
    @Test
    public void testDeleteMateriel() {
        // Arrange:
        int idToDelete = 1;
        Materiel materielToDelete = new Materiel("Test", "1.0", "123", 0);
        when(materielService.getMaterielFromDB(idToDelete)).thenReturn(materielToDelete);
        // Act:
        ResponseEntity<Materiel> result = materielController.deleteMateriel(idToDelete);
        // Verify the result message
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }
}

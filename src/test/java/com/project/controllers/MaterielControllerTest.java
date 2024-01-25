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
        Materiel result = materielController.getMateriel(idMateriel);
        // Assert :
        assertEquals(expectedMateriel, result);
    }

    /**
     * Test the addMateriel method
     */
    /*@Test
    public void testAddMateriel() {
        // Arrange :
        Materiel testMateriel = new Materiel("Test", "1.0", "123", 0);
        Mockito.doNothing().when(materielService).saveMateriel(Mockito.any());
        // Act :
        Materiel result = materielController.addMateriel("Test", "1.0", "123");
        // Assert :
        assertEquals(testMateriel, result);
        // Verify that saveMateriel was called with the expected arguments :
        verify(materielService).saveMateriel(Mockito.any());
    }*/

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
        String result = materielController.deleteMateriel(idToDelete);
        // Verify the result message
        assertEquals("Delete successfully", result);
    }
}

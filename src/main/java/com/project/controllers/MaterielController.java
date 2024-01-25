/**
 * Represents Materiel controller
 */
package com.project.controllers;

import com.project.services.MaterielService;
import com.project.entities.Materiel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MaterielController {
    // Attributes :
    private final MaterielService materielService;

    /**
     * Initialize object
     * @param materielService MaterielService
     */
    @Autowired
    public MaterielController(MaterielService materielService) {
        this.materielService = materielService;
    }

    /**
     * Get a Materiel
     * @param idMateriel id
     * @return Materiel if exists, null otherwise
     */
    @GetMapping("/get/{idMateriel}")
    public Materiel getMateriel(@PathVariable int idMateriel) {
        // Check if id is valid :
        if (idMateriel > 0) {
            return materielService.getMaterielFromDB(idMateriel);
        }

        return null;
    }

    /**
     * Add new materiel
     * @param nom nom
     * @param version version
     * @param ref ref
     */
    @PostMapping("/add")
    public Materiel addMateriel(@RequestParam String nom, @RequestParam String version, @RequestParam String ref) {
        // Check if parameters are valid :
        if (!nom.isEmpty() && !version.isEmpty() && !ref.isEmpty()) {
            // Create materiel object :
            Materiel materiel = new Materiel(nom, version, ref, 0);
            // Add materiel :
            materielService.saveMateriel(materiel);

            return materiel;
        }

        return null;
    }

    /**
     * Update materiel
     * @param idMateriel id
     * @param nom nom
     */
    @PutMapping("/update/{idMateriel}")
    public void updateMateriel(@PathVariable int idMateriel, @RequestParam String nom) {
        // Get materiel :
        Materiel materiel = materielService.getMaterielFromDB(idMateriel);
        // If materiel was found :
        if (materiel != null) {
            // Update values :
            materiel.setNom(nom);
            // Save :
            materielService.saveMateriel(materiel);
        }
    }

    /**
     * Delete a materiel
     * @param idMateriel id
     * @return message
     */
    @DeleteMapping("/delete/{idMateriel}")
    public String deleteMateriel(@PathVariable int idMateriel) {
        // Check if id is valid :
        if (idMateriel > 0) {
            // Delete materiel :
            materielService.deleteMateriel(idMateriel);

            return "Delete successfully";
        }

        return "id is not valid";
    }
}

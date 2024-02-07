/**
 * Represents Materiel controller
 */
package com.project.controllers;

import com.project.services.MaterielService;
import com.project.entities.Materiel;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@CrossOrigin(origins = "*")
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
     * Get All from materials
     * @return ResponseEntity
     */
    @GetMapping("/materiels")
    public ResponseEntity<List<Materiel>> getAllMateriels() {
        // Get all materials :
        List<Materiel> materiels = materielService.getAll();
        // Check if list is empty :
        if (materiels.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(materiels, HttpStatus.OK);
    }

    /**
     * Get a Materiel
     * @param idMateriel id
     * @return ResponseEntity
     */
    @GetMapping("/get/{idMateriel}")
    public ResponseEntity<Materiel> getMateriel(@PathVariable int idMateriel) {
        Materiel materiel = materielService.getMaterielFromDB(idMateriel);
        // Check if materiel is not null :
        if ( materiel != null) {
            return new ResponseEntity<>(materiel, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Add new materiel
     * @param nom nom
     * @param version version
     * @param ref ref
     * @return ResponseEntity
     */
    @PostMapping("/add")
    public ResponseEntity<Materiel> addMateriel(@RequestParam String nom, @RequestParam String version, @RequestParam String ref) {
        // Check if parameters are valid :
        if (!nom.isEmpty() && !version.isEmpty() && !ref.isEmpty()) {
            // Create materiel object :
            Materiel materiel = new Materiel(nom, version, ref, 0);
            // Add materiel :
            return new ResponseEntity<>(materielService.saveMateriel(materiel), HttpStatus.CREATED);
        }

        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Update materiel
     * @param idMateriel id
     * @param nom nom
     * @param version version
     * @param ref ref
     * @return ResponseEntity
     */
    @PutMapping("/update/{idMateriel}")
    public ResponseEntity<Materiel> updateMateriel(@PathVariable int idMateriel, @RequestParam String nom,
                               @RequestParam String version, @RequestParam String ref) {
        // Get materiel :
        Materiel materiel = materielService.getMaterielFromDB(idMateriel);
        // If materiel was found :
        if (materiel != null) {
            // Update values :
            materiel.setNom(nom);
            materiel.setVersion(version);
            materiel.setRef(ref);
            // Save :
            return new ResponseEntity<>(materielService.saveMateriel(materiel), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    /**
     * Delete a materiel
     * @param idMateriel id
     * @return ResponseEntity
     */
    @DeleteMapping("/delete/{idMateriel}")
    public ResponseEntity<Materiel> deleteMateriel(@PathVariable int idMateriel) {
        try {
            materielService.deleteMateriel(idMateriel);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

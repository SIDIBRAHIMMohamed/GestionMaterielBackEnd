/**
 * Represents Materiel service
 */
package com.project.services;

import com.project.entities.Materiel;
import com.project.repositories.MaterielRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
public class MaterielService {
    // Attributes :
    private final MaterielRepository materielRepository;

    /**
     * Initialize object
     * @param materielRepository MaterielRepository
     */
    @Autowired
    public MaterielService(MaterielRepository materielRepository) {
        this.materielRepository = materielRepository;
    }

    /**
     * Get All from Materiel table
     * @return List of Materiel
     */
    public List<Materiel> getAll() {
        return materielRepository.findAll();
    }

    /**
     * Get all materials with pagination
     * @param pageable Pageable object specifying page number and size
     * @return Page containing Materiel objects
     */
    public Page<Materiel> getAll(Pageable pageable) {
        return materielRepository.findAll(pageable);
    }

    /**
     * Get materiel from DB
     * @param id id
     * @return Materiel
     */
    public Materiel getMaterielFromDB(int id) {
        return materielRepository.getMaterielByIdMateriel(id);
    }

    /**
     * Save materiel
     * @param materiel Materiel
     * @return Materiel
     */
    public Materiel saveMateriel(Materiel materiel) {
        return materielRepository.save(materiel);
    }

    /**
     * Delete materiel
     * @param id id
     */
    public void deleteMateriel(int id) {
        materielRepository.deleteById(id);
    }
}

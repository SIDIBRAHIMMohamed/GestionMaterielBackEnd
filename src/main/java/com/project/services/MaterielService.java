/**
 * Represents Materiel service
 */
package com.project.services;

import com.project.entities.Materiel;
import com.project.repositories.MaterielRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     */
    public void saveMateriel(Materiel materiel) {
        materielRepository.save(materiel);
    }

    /**
     * Delete materiel
     * @param id id
     */
    public void deleteMateriel(int id) {
        materielRepository.deleteById(id);
    }
}

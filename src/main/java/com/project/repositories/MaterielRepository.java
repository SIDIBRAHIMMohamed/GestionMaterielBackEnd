/**
 * Represents Materiel repository
 */

package com.project.repositories;

import com.project.entities.Materiel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterielRepository extends JpaRepository<Materiel, Integer> {
    /**
     * Find Materiel by id
     * @param idMateriel id
     * @return Materiel
     */
    Materiel getMaterielByIdMateriel(int idMateriel);
}

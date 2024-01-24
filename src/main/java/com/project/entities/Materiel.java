/**
 * Represents Materiel in our app
 */
package com.project.entities;

import jakarta.persistence.*;

@Entity
@Table
public class Materiel {
    // Attributes :
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMateriel;
    private String nom;
    private String version;
    private String ref;
    private int status;

    /**
     * Default constructor
     */
    public Materiel() {
    }

    /**
     * Initialize object
     * @param nom nom
     * @param version version
     * @param ref ref
     * @param status status
     */
    public Materiel(String nom, String version, String ref, int status) {
        this.nom = nom;
        this.version = version;
        this.ref = ref;
        this.status = status;
    }

    /**
     * Get id
     * @return idMateriel
     */
    public int getIdMateriel() {
        return idMateriel;
    }

    /**
     * Get nom
     * @return nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Set nom
     * @param nom nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Get version
     * @return version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Set version
     * @param version version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Get ref
     * @return ref
     */
    public String getRef() {
        return ref;
    }

    /**
     * Set ref
     * @param ref ref
     */
    public void setRef(String ref) {
        this.ref = ref;
    }

    /**
     * Get status
     * @return status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Set status
     * @param status status
     */
    public void setStatus(int status) {
        this.status = status;
    }
}

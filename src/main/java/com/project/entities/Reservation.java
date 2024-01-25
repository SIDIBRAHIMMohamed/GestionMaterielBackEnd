/**
 * Represents Reservation in our app
 */
package com.project.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table
public class Reservation {
    // attributes :
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservation;
    private Date dateDebut;
    private Date dateFin;
    @ManyToOne
    @JoinColumn(name = "idUtilisateur", nullable = false)
    private Utilisateur utilisateur;
    @ManyToOne
    @JoinColumn(name = "idMateriel", nullable = false)
    private Materiel materiel;

    /**
     * Default constructor
     */
    public Reservation() {
    }

    /**
     * Initialize object
     * @param dateDebut start date
     * @param dateFin end date
     * @param utilisateur user
     * @param materiel material
     */
    public Reservation(Date dateDebut, Date dateFin, Utilisateur utilisateur, Materiel materiel) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.utilisateur = utilisateur;
        this.materiel = materiel;
    }

    /**
     * Get dateDebut
     * @return dateDebut
     */
    public Date getDateDebut() {
        return dateDebut;
    }

    /**
     * Get dateFin
     * @return dateFin
     */
    public Date getDateFin() {
        return dateFin;
    }

    /**
     * Get utilisateur
     * @return utilisateur
     */
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    /**
     * Get materiel
     * @return materiel
     */
    public Materiel getMateriel() {
        return materiel;
    }
}

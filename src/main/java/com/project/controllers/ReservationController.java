/**
 * Represents Reservation controller
 */
package com.project.controllers;

import com.project.entities.Materiel;
import com.project.entities.Reservation;
import com.project.entities.Utilisateur;
import com.project.services.MaterielService;
import com.project.services.ReservationService;
import com.project.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
public class ReservationController {
    // Attributes :
    private final ReservationService reservationService;
    private final UtilisateurService utilisateurService;
    private final MaterielService materielService;

    /**
     * Initialize object
     * @param reservationService ReservationService
     * @param utilisateurService UtilisateurService
     * @param materielService MaterielService
     */
    @Autowired
    public ReservationController(ReservationService reservationService, UtilisateurService utilisateurService, MaterielService materielService) {
        this.reservationService = reservationService;
        this.utilisateurService = utilisateurService;
        this.materielService = materielService;
    }

    /**
     * Get all reservations
     * @return ResponseEntity
     */
    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        // Get reservations :
        List<Reservation> reservations = reservationService.getAll();
        // Check if list is empty :
        if (reservations.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    /**
     * Make reservation for a material
     * @param dateDebut start date
     * @param dateFin end date
     * @param idUtilisateur user id
     * @param idMateriel material id
     * @return ResponseEntity
     */
    @PostMapping("/makeReservation")
    public ResponseEntity<Reservation> makeReservation(@RequestParam String dateDebut, @RequestParam String dateFin,
                                          @RequestParam Long idUtilisateur, @RequestParam int idMateriel) throws ParseException {
        // Get user :
        Optional<Utilisateur> utilisateurOptional = utilisateurService.obtenirUtilisateurParId(idUtilisateur);
        // Get material :
        Materiel materiel = materielService.getMaterielFromDB(idMateriel);
        // Check if material and user are valid :
        if (materiel == null || utilisateurOptional.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // Get dates :
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateDeDebut = dateFormat.parse(dateDebut);
        Date dateDeFin = dateFormat.parse(dateFin);
        // save reservation :
        Reservation reservation = reservationService.saveReservation(new Reservation(dateDeDebut, dateDeFin, utilisateurOptional.get(), materiel));
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }
}

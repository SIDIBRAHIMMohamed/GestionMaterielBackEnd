/**
 * Represents Reservation controller test class
 */
package com.project.controllers;

import com.project.entities.Materiel;
import com.project.entities.Reservation;
import com.project.entities.Utilisateur;
import com.project.services.MaterielService;
import com.project.services.ReservationService;
import com.project.services.UtilisateurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ReservationControllerTest {
    // Attributes :
    @Mock
    private ReservationService reservationService;
    @Mock
    private UtilisateurService utilisateurService;
    @Mock
    private MaterielService materielService;
    @InjectMocks
    private ReservationController reservationController;

    /**
     * To set up any necessary configurations
     */
    @BeforeEach
    public void setUp() {
        // To set up any necessary configurations
    }

    /**
     * Test makeReservation method
     */
    @Test
    public void testMakeReservation() {
        try {
            // arrange :
            Optional<Utilisateur> optionalUtilisateur = Optional.of(new Utilisateur("John", "Doe", "john.doe@example.com", "password123", 1));
            int idMateriel = 1;
            Materiel materiel = new Materiel("Test", "1.0", "123", 0);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            // Add mocks :
            when(utilisateurService.obtenirUtilisateurParId(any())).thenReturn(optionalUtilisateur);
            when(materielService.getMaterielFromDB(idMateriel)).thenReturn(materiel);
            when(reservationService.saveReservation(any(Reservation.class))).thenReturn(
                    new Reservation(simpleDateFormat.parse("2022-02-01"), simpleDateFormat.parse("2022-02-10"), optionalUtilisateur.get(), materiel)
            );
            // Act :
            Reservation result = reservationController.makeReservation("2022-02-01",
                    "2022-02-10", optionalUtilisateur.get().getId(), idMateriel);
            // Assert :
            assertEquals(simpleDateFormat.parse("2022-02-01"), result.getDateDebut());
            assertEquals(simpleDateFormat.parse("2022-02-10"), result.getDateFin());
            assertEquals(optionalUtilisateur.get(), result.getUtilisateur());
            assertEquals(materiel, result.getMateriel());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

/**
 * Represents Reservation service
 */
package com.project.services;

import com.project.entities.Reservation;
import com.project.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReservationService {
    // Attributes :
    private final ReservationRepository reservationRepository;

    /**
     * Initialize object
     * @param reservationRepository ReservationRepository
     */
    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    /**
     * Get all reservations
     * @return List of reservations
     */
    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    /**
     * Save reservation
     * @param reservation Reservation
     * @return Reservation
     */
    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }
}

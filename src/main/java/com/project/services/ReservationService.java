/**
 * Represents Reservation service
 */
package com.project.services;

import com.project.entities.Reservation;
import com.project.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * Save reservation
     * @param reservation Reservation
     */
    public void saveReservation(Reservation reservation) {
        reservationRepository.save(reservation);
    }
}

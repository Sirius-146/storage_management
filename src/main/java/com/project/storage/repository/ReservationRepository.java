package com.project.storage.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.storage.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Integer>{   

    @Query("""
        SELECT r FROM Reservation r
        WHERE r.apartment.id = :apartmentId
        AND r.status = 'CONFIRMED'
        AND (
            (:plannedCheckin < r.plannedCheckout AND :plannedCheckout > r.plannedCheckin)
        )
    """)
    List<Reservation> findConflictReservations(
        @Param("apartmentId") Integer apartmentId,
        @Param("plannedCheckin") LocalDate plannedCheckin,
        @Param("plannedChecout") LocalDate plannedCheckout
    );
}

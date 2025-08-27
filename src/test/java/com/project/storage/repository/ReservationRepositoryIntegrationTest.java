package com.project.storage.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.project.storage.model.Apartment;
import com.project.storage.model.Client;
import com.project.storage.model.Reservation;
import com.project.storage.model.ReservationStatus;

@DataJpaTest
@ActiveProfiles("test")
public class ReservationRepositoryIntegrationTest {
    
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Test
    void shouldFindConflictingReservations(){
        Client client = new Client();
        client.setName("João Teste");
        client = clientRepository.save(client);

        Apartment apt = new Apartment();
        apt.setNumber(101);
        apt.setType("Standard");
        apt = apartmentRepository.save(apt);

        Reservation existing = new Reservation();
        existing.setClient(client);
        existing.setApartment(apt);
        existing.setPlannedCheckin(LocalDate.of(2025, 9,10));
        existing.setPlannedCheckout(LocalDate.of(2025, 9,15));
        existing.setDailyRate(BigDecimal.valueOf(978.80));
        existing.setStatus(ReservationStatus.CONFIRMED);
        reservationRepository.save(existing);

        List<Reservation> conflicts = reservationRepository.findConflictingReservations(
            apt.getId(),
            LocalDate.of(2025, 9, 12),
            LocalDate.of(2025,9, 14)
        );

        assertFalse(conflicts.isEmpty());
        assertEquals(existing.getId(), conflicts.get(0).getId());
    }

        @Test
    void shouldNotFindConflictingReservations() {
        // Arrange
        Client client = new Client();
        client.setName("Maria Teste");
        client = clientRepository.save(client);

        Apartment apt = new Apartment();
        apt.setNumber(102);
        apt.setType("Deluxe");
        apt = apartmentRepository.save(apt);

        Reservation existing = new Reservation();
        existing.setClient(client);
        existing.setApartment(apt);
        existing.setPlannedCheckin(LocalDate.of(2025, 9, 10));
        existing.setPlannedCheckout(LocalDate.of(2025, 9, 15));
        existing.setDailyRate(BigDecimal.valueOf(978.80));
        existing.setStatus(ReservationStatus.CONFIRMED);
        reservationRepository.save(existing);

        // Act
        List<Reservation> conflicts = reservationRepository.findConflictingReservations(
            apt.getId(),
            LocalDate.of(2025, 9, 16), // começa depois do checkout
            LocalDate.of(2025, 9, 18)
        );

        // Assert
        assertTrue(conflicts.isEmpty());
    }
}

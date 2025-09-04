package com.project.storage.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.project.storage.dto.reservation.ReservationDTO;
import com.project.storage.handler.ApartmentUnavailableException;
import com.project.storage.model.Apartment;
import com.project.storage.model.Client;
import com.project.storage.model.Reservation;
import com.project.storage.model.ReservationStatus;
import com.project.storage.repository.ApartmentRepository;
import com.project.storage.repository.ClientRepository;
import com.project.storage.repository.ReservationGroupRepository;
import com.project.storage.repository.ReservationRepository;

public class ReservationServiceTest {
    
    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ApartmentRepository apartmentRepository;

    @Mock
    private ReservationGroupRepository groupRepository;

    @InjectMocks
    private ReservationService reservationService;

    private Client client;
    private Apartment apartment;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        client = new Client();
        client.setId(1);

        apartment = new Apartment();
        apartment.setId(10);
    }

    @Test
    void shouldCreateReservationWhenApartmentIsAvailable(){
        //Arrange
        ReservationDTO dto = new ReservationDTO(
            LocalDate.of(2025, 9,11),
            LocalDate.of(2025, 9,15),
            client.getId(),
            apartment.getId(),
            null
        );

        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));
        when(apartmentRepository.findById(apartment.getId())).thenReturn(Optional.of(apartment));
        when(reservationRepository.findConflictingReservations(
            apartment.getId(),
            dto.plannedCheckin(),
            dto.plannedCheckout()
        )).thenReturn(List.of());
        when(reservationRepository.save(any(Reservation.class))).thenAnswer(inv -> inv.getArgument(0));

        Reservation reservation = reservationService.createReservation(dto);

        assertNotNull(reservation);
        assertEquals(client, reservation.getClient());
        assertEquals(apartment, reservation.getApartment());
        assertEquals(dto.plannedCheckin(), reservation.getPlannedCheckin());
        assertEquals(dto.plannedCheckout(), reservation.getPlannedCheckout());
        assertEquals(ReservationStatus.PENDING, reservation.getStatus());
    }

    @Test
    void shouldThrowExceptionWhenApartmentIsUnavailable() {
        // Arrange
        ReservationDTO dto = new ReservationDTO(
            LocalDate.of(2025, 9, 11),
            LocalDate.of(2025, 9, 15),
            client.getId(),
            apartment.getId(),
            null
        );

        Reservation existing = new Reservation();
        existing.setId(99);

        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));
        when(apartmentRepository.findById(apartment.getId())).thenReturn(Optional.of(apartment));
        when(reservationRepository.findConflictingReservations(
                apartment.getId(),
                dto.plannedCheckin(),
                dto.plannedCheckout()
        )).thenReturn(List.of(existing)); // conflito detectado

        // Act & Assert
        Exception ex = assertThrows(ApartmentUnavailableException.class, () -> {
            reservationService.createReservation(dto);
        });

        assertEquals("Apartamento indisponível para o período selecionado", ex.getMessage());
    }
}

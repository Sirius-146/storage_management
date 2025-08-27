package com.project.storage.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.storage.dto.ReservationDTO;
import com.project.storage.handler.ApartmentUnavailableException;
import com.project.storage.handler.NotFoundException;
import com.project.storage.model.Apartment;
import com.project.storage.model.Client;
import com.project.storage.model.Reservation;
import com.project.storage.model.ReservationGroup;
import com.project.storage.model.ReservationStatus;
import com.project.storage.repository.ApartmentRepository;
import com.project.storage.repository.ClientRepository;
import com.project.storage.repository.ReservationGroupRepository;
import com.project.storage.repository.ReservationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ClientRepository clientRepository;
    private final ApartmentRepository apartmentRepository;
    private final ReservationGroupRepository groupRepository;

    public Reservation createReservation(ReservationDTO dto) {
        Client client = clientRepository.findById(dto.clientId())
                .orElseThrow(() -> new NotFoundException("Client"));
        
        Apartment apartment = apartmentRepository.findById(dto.apartmentId())
                .orElseThrow(() -> new NotFoundException("Apartment"));

        ReservationGroup group = null;
        if (dto.groupId() != null) {
            group = groupRepository.findById(dto.groupId())
                    .orElseThrow(() -> new NotFoundException("Group"));
        }

        List<Reservation> conflicts = reservationRepository.findConflictReservations(
            apartment.getId(),
            dto.plannedCheckin(),
            dto.plannedCheckout()
        );
        
        if (!conflicts.isEmpty()){
            throw new ApartmentUnavailableException();
        }

        Reservation reservation = new Reservation();
        reservation.setPlannedCheckin(dto.plannedCheckin());
        reservation.setPlannedCheckout(dto.plannedCheckout());
        reservation.setClient(client);
        reservation.setApartment(apartment);
        reservation.setGroup(group);
        
        reservation.setStatus(ReservationStatus.PENDING);

        return reservationRepository.save(reservation);     
    }
    
    public Reservation confirmReservation(Integer reservationId){
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException("Reservation"));
        
        if (reservation.getStatus() != ReservationStatus.PENDING){
            throw new IllegalStateException("Somente reservas pendentes podem ser confirmadas");
        }

        reservation.setStatus(ReservationStatus.CONFIRMED);
        
        return reservationRepository.save(reservation);
    }

    public Reservation checkIn(Integer reservationId){
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException("Reservation"));
        
        if (reservation.getStatus() != ReservationStatus.CONFIRMED){
            throw new IllegalStateException("Somente reservas confirmadas podem realizar checkin");
        }

        reservation.setCheckin(LocalDateTime.now());
        reservation.setStatus(ReservationStatus.CHECKED_IN);

        return reservationRepository.save(reservation);
    }

    public Reservation checkOut(Integer reservationId){
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException("Reservation"));
        
        if (reservation.getStatus() != ReservationStatus.CHECKED_IN){
            throw new IllegalStateException("Somente reservas com checkin podem realizar checkout");
        }

        reservation.setCheckout(LocalDateTime.now());
        reservation.setStatus(ReservationStatus.CHECKED_OUT);

        return reservationRepository.save(reservation);
    }

    public Reservation cancelReservation(Integer reservationId){
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException("Reservation"));
        
        if (reservation.getStatus() == ReservationStatus.CHECKED_IN || reservation.getStatus() == ReservationStatus.CHECKED_OUT){
            throw new IllegalStateException("Não é possível cancelar reservas em andamento ou concluídas");
        }

        reservation.setStatus(ReservationStatus.CANCELED);

        return reservationRepository.save(reservation);
    }
    public List<Reservation> listReservations() {
        return reservationRepository.findAll();
    }
}

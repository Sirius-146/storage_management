package com.project.storage.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.storage.dto.ReservationDTO;
import com.project.storage.handler.NotFoundException;
import com.project.storage.model.Apartment;
import com.project.storage.model.Client;
import com.project.storage.model.Reservation;
import com.project.storage.model.ReservationGroup;
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

        Reservation reservation = new Reservation();
        reservation.setCheckin(dto.checkin());
        reservation.setCheckout(dto.checkout());
        reservation.setClient(client);
        reservation.setApartment(apartment);
        reservation.setGroup(group);

        return reservationRepository.save(reservation);     
    }

    public List<Reservation> listReservations() {
        return reservationRepository.findAll();
    }
}

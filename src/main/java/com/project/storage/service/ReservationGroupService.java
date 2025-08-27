package com.project.storage.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.storage.dto.ReservationDTO;
import com.project.storage.dto.ReservationGroupDTO;
import com.project.storage.model.Reservation;
import com.project.storage.model.ReservationGroup;
import com.project.storage.repository.ReservationGroupRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationGroupService {
    
    private final ReservationGroupRepository groupRepository;
    private final ReservationService reservationService;

    public ReservationGroup createGroup(ReservationGroupDTO dto){
        ReservationGroup group = new ReservationGroup();
        group.setGroupName(dto.groupName());

        ReservationGroup savedGroup = groupRepository.save(group);

        if (dto.reservations() != null){
            for (ReservationDTO r : dto.reservations()){
                Reservation reservation = reservationService.createReservation(
                    new ReservationDTO(
                        r.plannedCheckin(),
                        r.plannedCheckout(),
                        r.clientId(),
                        r.apartmentId(),
                        savedGroup.getId()
                    )
                );
                savedGroup.addReservation(reservation);
            }
        }

        return groupRepository.save(savedGroup);
    }

    public List<ReservationGroup> listGroups(){
        return groupRepository.findAll();
    }
}

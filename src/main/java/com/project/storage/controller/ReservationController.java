package com.project.storage.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.storage.dto.reservation.ReservationDTO;
import com.project.storage.model.Reservation;
import com.project.storage.service.ReservationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {
    
    private final ReservationService reservationService;

    @PostMapping
    public Reservation createReservation(@RequestBody ReservationDTO dto){
        return reservationService.createReservation(dto);
    }

    @GetMapping
    public List<Reservation> listReservation(){
        return reservationService.listReservations();
    }
}

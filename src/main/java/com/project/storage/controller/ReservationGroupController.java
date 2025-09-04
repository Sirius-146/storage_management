package com.project.storage.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.storage.dto.reservation.ReservationGroupDTO;
import com.project.storage.model.ReservationGroup;
import com.project.storage.service.ReservationGroupService;

@RestController
@RequestMapping("/reservation-groups")
public class ReservationGroupController {

    private final ReservationGroupService groupService;

    public ReservationGroupController(ReservationGroupService groupService){
        this.groupService = groupService;
    }

    @PostMapping
    public ReservationGroup createGroup(@RequestBody ReservationGroupDTO dto){
        return groupService.createGroup(dto);
    }

    @GetMapping
    public List<ReservationGroup> listGroups(){
        return groupService.listGroups();
    }
}

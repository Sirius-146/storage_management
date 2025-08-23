package com.project.storage.dto;

import java.util.List;

public record ReservationGroupDTO(
    String groupName,
    List<ReservationDTO> reservations
) {}

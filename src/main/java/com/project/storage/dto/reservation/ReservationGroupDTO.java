package com.project.storage.dto.reservation;

import java.util.List;

public record ReservationGroupDTO(
    String groupName,
    List<ReservationDTO> reservations
) {}

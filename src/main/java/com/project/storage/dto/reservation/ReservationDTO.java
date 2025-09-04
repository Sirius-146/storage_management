package com.project.storage.dto.reservation;

import java.time.LocalDate;

public record ReservationDTO (
    LocalDate plannedCheckin,
    LocalDate plannedCheckout,
    Integer clientId,
    Integer apartmentId,
    Integer groupId
) {}
package com.project.storage.dto;

import java.sql.Date;

public record ReservationDTO (
    Date checkin,
    Date checkout,
    Integer clientId,
    Integer apartmentId,
    Integer groupId
) {}
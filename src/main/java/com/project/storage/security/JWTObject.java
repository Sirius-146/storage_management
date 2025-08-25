package com.project.storage.security;

import java.util.Date;

import com.project.storage.model.Role;

public record JWTObject(
    String subject,
    Date issuedAt,
    Date expiration,
    Role role
) {}

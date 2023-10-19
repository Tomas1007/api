package com.ejemplo.api.dto;

public record UserUpdDto(
        String name,
        String lastname,
        String email,
        Long phoneNumber
) {
}

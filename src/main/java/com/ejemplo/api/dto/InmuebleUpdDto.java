package com.ejemplo.api.dto;

public record InmuebleUpdDto(
        String nombre,
        String descripcion,
        boolean pileta,
        boolean parrilla,
        UserDto userDto
) {
}

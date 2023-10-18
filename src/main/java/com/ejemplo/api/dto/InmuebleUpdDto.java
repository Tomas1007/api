package com.ejemplo.api.dto;

public record InmuebleUpdDto(
        String titulo,
        String descripcion,
        String localidad,
        String ubicacion,
        UserDto userDto
) {
}

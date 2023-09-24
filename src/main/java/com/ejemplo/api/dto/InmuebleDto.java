package com.ejemplo.api.dto;

import com.ejemplo.api.entidades.User;


import java.util.Date;


public record InmuebleDto(
        String nombre,
        String descripcion,
        Date fechaCreacion,
        boolean pileta,
        boolean parrilla,
        UserDto userDto
        ) {
}

package com.ejemplo.api.dto;

import com.ejemplo.api.entidades.User;


import java.math.BigDecimal;
import java.util.Date;


public record InmuebleDto(
        String nombre,
        String descripcion,
        Date fechaCreacion,
        BigDecimal precio,
        boolean pileta,
        boolean parrilla,
        UserDto userDto
        ) {
}

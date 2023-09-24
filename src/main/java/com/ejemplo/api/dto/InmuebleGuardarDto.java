package com.ejemplo.api.dto;

import java.util.Date;

public record InmuebleGuardarDto(
        String nombre,
        String descripcion,
        Date fechaCreacion,
        boolean pileta,
        boolean parrilla
) {
}

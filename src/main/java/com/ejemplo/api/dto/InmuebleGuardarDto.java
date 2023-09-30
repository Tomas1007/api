package com.ejemplo.api.dto;

import java.math.BigDecimal;
import java.util.Date;

public record InmuebleGuardarDto (
        String nombre,
        String descripcion,
        Date fechaCreacion,

        BigDecimal precio,
        boolean pileta,
        boolean parrilla
) {

}

package com.ejemplo.api.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public record InmuebleWithPortada(
        Integer id,
        String titulo,
        String descripcion,
        Date fechaCreacion,
        BigDecimal precio,
        String localidad,
        String ubicacion,
        boolean pileta,
        boolean parrilla,
        String portada,
        List<String> contenido,
        String nombreUsuario
) {
}

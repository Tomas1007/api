package com.ejemplo.api.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public record InmuebleAllDto(
        Integer id,
        String titulo,
        String descripcion,
        Date fechaCreacion,
        BigDecimal precio,
        String localiada,
        String ubicacion,
        boolean pileta,
        boolean parrilla,
        List<String> filePath,
        List<String> contenido,
        String nombreUsuario
) {
}

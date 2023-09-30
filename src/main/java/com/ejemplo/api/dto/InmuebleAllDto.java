package com.ejemplo.api.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public record InmuebleAllDto(
        Integer id,
        String nombre,
        String descripcion,
        Date fechaCreacion,
        BigDecimal precio,
        boolean pileta,
        boolean parrilla,
        List<String> filePath,
        List<String> contenido,
        String nombreUsuario
) {
}

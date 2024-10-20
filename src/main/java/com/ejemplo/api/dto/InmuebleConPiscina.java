package com.ejemplo.api.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public record InmuebleConPiscina(
        Integer id,
        String titulo,
        String descripcion,
        Date fechaCreacion,
        BigDecimal precio,
        String localidad,
        String ubicacion,
        String portada,
        List<String> contenido,
        Integer habitaciones,
        Integer cantidadPersonas,

        String nombreUsuario,

        boolean piscina
) {
}

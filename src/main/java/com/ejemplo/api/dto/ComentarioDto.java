package com.ejemplo.api.dto;
import java.util.Date;

public record ComentarioDto(
        String nombreInmueble,
        String nombreUsuario,
        String contenido,
        Date fecha
) {
}

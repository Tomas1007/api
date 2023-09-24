package com.ejemplo.api.dto;

import java.util.Date;

public record ComentarioGuardarDto(
        String contenido,
        Date fechaCreacion
) {
}

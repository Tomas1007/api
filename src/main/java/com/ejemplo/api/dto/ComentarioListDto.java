package com.ejemplo.api.dto;

import com.ejemplo.api.entidades.User;

import java.util.Date;
import java.util.List;
import java.util.Set;

public record ComentarioListDto(
        String nombreInmueble,
        List<String> filePath,
        List<String> contenido,
        List<Date> fecha,
        List<String> nombreUsuario
) {
}

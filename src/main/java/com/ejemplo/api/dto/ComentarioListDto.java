package com.ejemplo.api.dto;



import java.util.Date;



public record ComentarioListDto(
       String contenido,
       Date fechaCreacion,
       String nombreUsuario
) {
}

package com.ejemplo.api.dto;

public record ImagenDto(
        Integer id,
        String nombre,
        String tipo,
        String filePath,
        String nombrePublicacion
) {
}

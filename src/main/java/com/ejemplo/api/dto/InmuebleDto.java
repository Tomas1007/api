package com.ejemplo.api.dto;




import java.math.BigDecimal;
import java.util.Date;


public record InmuebleDto(
        Integer id,
        String titulo,
        String descripcion,
        Date fechaCreacion,
        BigDecimal precio,
        String localidad,
        String ubicacion,
        boolean pileta,
        boolean parrilla,
        UserDto userDto
        ) {
}

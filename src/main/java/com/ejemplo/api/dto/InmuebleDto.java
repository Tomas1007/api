package com.ejemplo.api.dto;




import java.math.BigDecimal;
import java.util.Date;


public record InmuebleDto(
        String titulo,
        String descripcion,
        Date fechaCreacion,
        BigDecimal precio,
        String locaclidad,
        String ubicacion,
        boolean pileta,
        boolean parrilla,
        UserDto userDto
        ) {
}

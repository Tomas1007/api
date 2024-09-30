package com.ejemplo.api.dto;


import java.math.BigDecimal;
import java.util.Date;

public record ReservaDto (

        String tituloInmueble,
        BigDecimal precio,

        Date fechaReserva,

        String nombreUsuario,
        String email

){

}

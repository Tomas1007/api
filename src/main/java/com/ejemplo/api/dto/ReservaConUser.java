package com.ejemplo.api.dto;



import java.math.BigDecimal;
import java.util.Date;

public record ReservaConUser(

        String nameUser,
        String userEmail,

        String titulo,
        BigDecimal precio,

        Date fechaReserva

) {
}

package com.ejemplo.api.servicio;

import com.ejemplo.api.dto.ReservaDto;
import com.ejemplo.api.entidades.Reservas;

public interface ReservasServicio {

     ReservaDto guardarReserva(Reservas reservas, String email, Integer idINmueble);

    // Reservas reservaByUserEmail(String email);
}

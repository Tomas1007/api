package com.ejemplo.api.servicio;

import com.ejemplo.api.dto.ReservaConUser;
import com.ejemplo.api.dto.ReservaDto;
import com.ejemplo.api.entidades.Reservas;

import java.util.Date;
import java.util.List;

public interface ReservasServicio {

     ReservaDto guardarReserva(Reservas reservas, String email, Integer idINmueble);


      List<ReservaConUser> obtenerReservasPorUsuarioEInmueble(String email);

      ReservaDto reservaUpd (Integer idReserva, Date fecha, Integer idInmueble);

}

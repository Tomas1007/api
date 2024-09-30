package com.ejemplo.api.controlador;

import com.ejemplo.api.dto.ReservaConUser;
import com.ejemplo.api.dto.ReservaDto;
import com.ejemplo.api.entidades.Reservas;
import com.ejemplo.api.servicio.ReservasServicioImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class ReservasController {

private final ReservasServicioImpl reservasServicio;

@PostMapping("/user/inmueble")
    public ResponseEntity<ReservaDto> reservar(@RequestBody Reservas reservas,
                                               @RequestParam("email") String email,
                                               @RequestParam("id")Integer inmuebleId){
     return ResponseEntity.ok(reservasServicio.guardarReserva(reservas,email, inmuebleId));
}

@GetMapping("/user")
    public ResponseEntity<List<ReservaConUser>> reservasByUser(@RequestParam("email") String email){

    List<ReservaConUser> reservaConUserList = reservasServicio.obtenerReservasPorUsuarioEInmueble(email);

    return ResponseEntity.ok(reservaConUserList);
}
@PutMapping("/inmueble")
    public ResponseEntity<ReservaDto> reservaUpt(@RequestParam("fecha" )@DateTimeFormat(pattern = "dd/MM/yyyy") Date fecha,
                                                 @RequestParam("idReserva") Integer idReserva,
                                                 @RequestParam("idInmueble") Integer idInmueble){
    return ResponseEntity.ok(reservasServicio.reservaUpd(idReserva, fecha,idInmueble));
}

}

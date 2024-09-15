package com.ejemplo.api.controlador;

import com.ejemplo.api.dto.ReservaDto;
import com.ejemplo.api.entidades.Reservas;
import com.ejemplo.api.servicio.ReservasServicioImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}

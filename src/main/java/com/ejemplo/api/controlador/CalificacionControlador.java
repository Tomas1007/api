package com.ejemplo.api.controlador;

import com.ejemplo.api.entidades.Calificacion;
import com.ejemplo.api.servicio.CalificacionServicioImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calificacion")
@RequiredArgsConstructor
public class CalificacionControlador {

private final CalificacionServicioImpl calificacionServicio;


@GetMapping("/inmueble")
    public ResponseEntity<Double> obtenerPromedioCalificacion(@RequestParam("idInmueble") Integer id){
        double promedio = calificacionServicio.obtenerPromedio(id);
        return ResponseEntity.ok(promedio);
}
@PostMapping
    public ResponseEntity<String> enviarCalificacion(@RequestParam("calificacion")Integer calificacion,
                                                           @RequestParam("idInmueble")Integer idInmueble){
    return ResponseEntity.ok(calificacionServicio.guardar(calificacion,idInmueble));
}

}

package com.ejemplo.api.controlador;

import com.ejemplo.api.entidades.Caracteristicas;
import com.ejemplo.api.servicio.CaracteristicasServicioImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/caracteristicas")
@RequiredArgsConstructor
public class CarracteristicasControlador {

    private final CaracteristicasServicioImpl caracteristicasServicio;

    @PostMapping("/inmueble")
    public ResponseEntity<Caracteristicas> crear(@RequestBody Caracteristicas caracteristicas,
                                                 @RequestParam("id") Integer id){
        return ResponseEntity.ok(caracteristicasServicio.guardar(caracteristicas, id));
    }

    @GetMapping("/inmueble")
    public ResponseEntity<List<Caracteristicas>> listarCaracteristicasPorInmueble(@RequestParam("id") Integer id){

        return ResponseEntity.ok(caracteristicasServicio.listarCaracteristicasPorInmueble(id));
    }
    @PutMapping("/inmueble")
    public ResponseEntity<Caracteristicas> actualizar(@RequestBody Caracteristicas caracteristicas,
                                                 @RequestParam("id") Integer id){
        return ResponseEntity.ok(caracteristicasServicio.guardar(caracteristicas, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCaracteristicas(@PathVariable("id") Integer id){
        caracteristicasServicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

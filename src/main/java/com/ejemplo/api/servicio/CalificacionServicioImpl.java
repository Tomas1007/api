package com.ejemplo.api.servicio;

import com.ejemplo.api.entidades.Calificacion;
import com.ejemplo.api.entidades.Inmueble;
import com.ejemplo.api.repository.CalificacionRepo;
import com.ejemplo.api.repository.InmuebleRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CalificacionServicioImpl implements CalificacionServicio {

    private final InmuebleRepo inmuebleRepo;
    private final CalificacionRepo calificacionRepo;

    @Transactional
    @Override
    public String guardar(Integer calificacionResponse, Integer idInmueble) {
        try {
            if (idInmueble == null) {
                throw new IllegalArgumentException("Ingrese un id valido");
            }
            Inmueble inmueble = inmuebleRepo.findById(idInmueble)
                    .orElseThrow(() -> new NoSuchElementException("No se encontro el inmueble con ese Id"));

            Calificacion calificacion = new Calificacion();
            calificacion.setCalificacion(calificacionResponse);
            calificacion.setInmueble(inmueble);

            calificacionRepo.save(calificacion);
            return "Calificó el inmuueble correctamente";
        } catch (Exception e) {
            throw new RuntimeException("No se pudo guardar la calificacion");
        }
    }

    public double obtenerPromedio(Integer idInmueble) {
        try {
            if (idInmueble == null) {
                throw new IllegalArgumentException("Ingrese un id valido");
            }

            List<Calificacion> calificaciones = calificacionRepo.findByInmuebleId(idInmueble);

            double sum = calificaciones.stream().mapToInt(Calificacion::getCalificacion).sum();
            return sum / calificaciones.size();
        } catch (Exception e) {
            throw new RuntimeException("No se puede mostrar la calificacion");
        }
    }
}

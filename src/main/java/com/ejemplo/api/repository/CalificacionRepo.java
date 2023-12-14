package com.ejemplo.api.repository;

import com.ejemplo.api.entidades.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface CalificacionRepo extends JpaRepository<Calificacion, Integer> {

    List<Calificacion> findByInmuebleId (Integer inmuebleId);
}

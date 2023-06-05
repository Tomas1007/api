package com.ejemplo.api.repository;

import com.ejemplo.api.entidades.Inmueble;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InmuebleRepo extends JpaRepository<Inmueble, Integer> {
}

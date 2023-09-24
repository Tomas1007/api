package com.ejemplo.api.repository;

import com.ejemplo.api.entidades.Inmueble;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InmuebleRepo extends JpaRepository<Inmueble, Integer> {
    
    Page<Inmueble> findByPileta(@Param("pileta") boolean pileta, Pageable pageable);
    Page<Inmueble> findByParrilla(@Param("parrilla") boolean parrilla, Pageable pageable);
}

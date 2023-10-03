package com.ejemplo.api.repository;

import com.ejemplo.api.entidades.Inmueble;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InmuebleRepo extends JpaRepository<Inmueble, Integer> {

    Page<Inmueble> findByPileta(boolean pileta ,Pageable pageable);
    Page<Inmueble> findByParrilla(boolean parrilla, Pageable pageable);

    Page<Inmueble> findByParrillaAndPileta(boolean parrilla,boolean pileta, Pageable pageable);
}

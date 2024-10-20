package com.ejemplo.api.repository;

import com.ejemplo.api.entidades.Inmueble;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InmuebleRepo extends JpaRepository<Inmueble, Integer> {

    Page<Inmueble> findByUserEmail(String email, Pageable pageable);

    List<Inmueble> findByCaracteristicasPiscinaIsTrue ();
    List<Inmueble> findByCaracteristicasParrillaIsTrue();

    List<Inmueble> findByCaracteristicas_ParrillaTrueAndCaracteristicas_PiscinaTrue();


}

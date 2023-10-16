package com.ejemplo.api.repository;

import com.ejemplo.api.entidades.Comentario;
import com.ejemplo.api.entidades.Inmueble;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.stereotype.Repository;

@Repository
public interface ComentarioRepo extends JpaRepository<Comentario, Integer> {

  Page<Comentario> findByInmueble(Inmueble inmueble, Pageable pageable);
}
package com.ejemplo.api.repository;

import com.ejemplo.api.entidades.Comentario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentarioRepo extends JpaRepository<Comentario, Integer> {

    Page<Comentario> findByInmueble(Integer inmueble, Pageable pageable);
}

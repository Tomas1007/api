package com.ejemplo.api.repository;

import com.ejemplo.api.entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagenRepo extends JpaRepository<Imagen, Integer> {
}

package com.ejemplo.api.repository;

import com.ejemplo.api.entidades.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Integer> {
    Rol findByNombreRol(String user);
}

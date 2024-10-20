package com.ejemplo.api.repository;

import com.ejemplo.api.entidades.Caracteristicas;
import com.ejemplo.api.entidades.Inmueble;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
@Repository
public interface CaracteristicasRepo extends JpaRepository<Caracteristicas, Integer> {

    List<Caracteristicas> findByInmueble(Inmueble inmueble);

}

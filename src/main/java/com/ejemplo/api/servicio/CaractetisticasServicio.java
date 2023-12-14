package com.ejemplo.api.servicio;



import com.ejemplo.api.entidades.Caracteristicas;


import java.util.List;

public interface CaractetisticasServicio {

    Caracteristicas guardar(Caracteristicas caracteristicas, Integer idInmueble);

    List<Caracteristicas> listarCaracteristicasPorInmueble(Integer id);


    void eliminar(Integer id);
}

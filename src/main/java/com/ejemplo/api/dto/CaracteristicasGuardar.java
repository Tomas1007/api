package com.ejemplo.api.dto;

import com.ejemplo.api.entidades.Caracteristicas;

public record CaracteristicasGuardar(
        boolean piscina,
        boolean wifi,
        boolean parrilla,
        Integer cantidadPersonas,
        Integer habitaciones,
        Integer banios,

        Integer idInmueble

) {
}

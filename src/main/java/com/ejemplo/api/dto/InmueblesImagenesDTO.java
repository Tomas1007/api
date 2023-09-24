package com.ejemplo.api.dto;

import com.ejemplo.api.entidades.Inmueble;

import java.util.Date;
import java.util.List;


public record InmueblesImagenesDTO (
        Inmueble inmueble,
        List<String> filePath
){

}
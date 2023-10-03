package com.ejemplo.api.servicio;

import com.ejemplo.api.dto.InmuebleAllDto;
import com.ejemplo.api.dto.InmuebleDto;
import com.ejemplo.api.dto.InmuebleGuardarDto;
import com.ejemplo.api.dto.InmuebleUpdDto;
import com.ejemplo.api.entidades.Inmueble;

import java.util.List;


public interface InmuebleServicio {

    InmuebleDto guardar(InmuebleGuardarDto inmuebleGuardarDto, String email);


    List<InmuebleAllDto> listarTodo(int page, int size);

    InmuebleUpdDto actualizar(Integer id, Inmueble inmueble);

    void eliminar(Integer id);

}

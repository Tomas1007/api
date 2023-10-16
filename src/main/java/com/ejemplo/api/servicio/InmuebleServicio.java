package com.ejemplo.api.servicio;

import com.ejemplo.api.dto.*;
import com.ejemplo.api.entidades.Inmueble;

import java.util.List;


public interface InmuebleServicio {

    InmuebleDto guardar(InmuebleGuardarDto inmuebleGuardarDto, String email);

    List<InmuebleSinComentariosDto> listarTodoSinComentarios(int page, int size);

    InmuebleAllDto listarPorId(Integer id);

    InmuebleUpdDto actualizar(Integer id, Inmueble inmueble);

    void eliminar(Integer id);

}

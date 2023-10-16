package com.ejemplo.api.servicio;

import com.ejemplo.api.dto.ComentarioDto;
import com.ejemplo.api.dto.ComentarioGuardarDto;

import com.ejemplo.api.dto.ComentarioUpd;



public interface ComentarioServicio {


    ComentarioDto crearComentario(ComentarioGuardarDto comentarioGuardarDto, String email, Integer inmuebleId);

    ComentarioDto actualizarComentario(String email, Integer inmuebleId, ComentarioUpd comentarioUpd);

    void eliminarComentario(Integer idComentario);

    //Page<Comentario> listarComentarios(Integer inmuebleId, int page, int size);
}

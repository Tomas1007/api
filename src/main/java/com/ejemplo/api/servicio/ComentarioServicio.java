package com.ejemplo.api.servicio;

import com.ejemplo.api.dto.ComentarioDto;
import com.ejemplo.api.dto.ComentarioGuardarDto;
import com.ejemplo.api.dto.ComentarioListDto;
import com.ejemplo.api.dto.ComentarioUpd;


public interface ComentarioServicio {


    ComentarioDto crearComentario(ComentarioGuardarDto comentarioGuardarDto, Integer userId, Integer inmuebleId);

    ComentarioDto actualizarComentario(Integer idUser, Integer inmuebleId, ComentarioUpd comentarioUpd);

    void eliminarComentario(Integer idComentario);

    ComentarioListDto listarComentarios(Integer inmuebleId, int page, int size);
}

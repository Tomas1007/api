package com.ejemplo.api.servicio;

import com.ejemplo.api.entidades.Imagen;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ImagenPortadaServicio {

    String subirImagenPortada(MultipartFile file, Integer id) throws IOException;

    List<Imagen> listaImagenPortada(int page, int size);

    String eliminarIamgenPortada(Integer id);

    void actualizar (Imagen imagen, Integer id);
}

package com.ejemplo.api.servicio;



import com.ejemplo.api.entidades.Imagen;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface ImagenServicio {

    String subirImagen(MultipartFile[] files, Integer id) throws IOException;

    List<Imagen> listaImagenes(int page, int size);

    String eliminarIamgen(Integer id);

    void actualizar (Imagen imagen, Integer id);

}

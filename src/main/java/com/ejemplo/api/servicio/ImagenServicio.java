package com.ejemplo.api.servicio;

import com.ejemplo.api.entidades.Imagen;
import com.ejemplo.api.entidades.Inmueble;
import com.ejemplo.api.repository.ImagenRepo;
import com.ejemplo.api.repository.InmuebleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
public class ImagenServicio {

    @Autowired
    private ImagenRepo imagenRepo;
    @Autowired
    private InmuebleRepo inmuebleRepo;
    private final String CARPETA_PATH = "C:\\Users\\sbria\\OneDrive\\Escritorio\\misImagenes\\";

    public String subirImagen(MultipartFile[] files, Integer idInmueble) throws IOException {
        Inmueble inmueble = inmuebleRepo.findById(idInmueble).get();
        if (inmueble != null) {

            for (MultipartFile archivo :files) {
                String filePath = CARPETA_PATH + archivo.getOriginalFilename();

                Imagen imagen = new Imagen();
                imagen.setNombre(archivo.getOriginalFilename());
                imagen.setTipo(archivo.getContentType());
                imagen.setInmueble(inmueble);
                imagen.setFilePath(filePath);

                imagenRepo.save(imagen);
                try{
                    File file = new File(filePath);
                    archivo.transferTo(file);
                }catch (Exception e){
                    return "Error al cargar las imagenes " + archivo.getOriginalFilename();
                }

            }
            return "Imagenes subidas correctamente";
        }else{
            return "No se encontro el ID de inmueble "+ idInmueble;
        }
    }
}
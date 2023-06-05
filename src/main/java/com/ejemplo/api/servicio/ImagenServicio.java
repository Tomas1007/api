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

    public String subirImagen(MultipartFile file, Integer idInmueble) throws IOException {
        Inmueble inmueble = inmuebleRepo.findById(idInmueble).get();
        if (inmueble != null) {

            String filePath = CARPETA_PATH + file.getOriginalFilename();

            Imagen imagen = imagenRepo.save(Imagen.builder()
                    .nombre(file.getOriginalFilename())
                    .tipo(file.getContentType())
                    .inmueble(inmueble)
                    .filePath(filePath)
                    .build());
            file.transferTo(new File(filePath));
            if (imagen != null) {
                return "Se cargaron correctamente las imagenes " + file.getOriginalFilename();
            }
            }
            return null;
        }

}

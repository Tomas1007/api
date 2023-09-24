package com.ejemplo.api.servicio;


import com.ejemplo.api.entidades.Imagen;
import com.ejemplo.api.entidades.Inmueble;
import com.ejemplo.api.repository.ImagenRepo;
import com.ejemplo.api.repository.InmuebleRepo;
import lombok.RequiredArgsConstructor;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

import java.util.NoSuchElementException;
@Service
@RequiredArgsConstructor
public class ImagenServicioImpl implements ImagenServicio {


    private final ImagenRepo imagenRepo;

    private final InmuebleRepo inmuebleRepo;
    private final String CARPETA_PATH = "C:\\Users\\sbria\\OneDrive\\Escritorio\\misImagenes\\";

    @Override
    public String subirImagen(MultipartFile[] files, Integer idInmueble) throws IOException {
        Inmueble inmueble = inmuebleRepo.findById(idInmueble)
                .orElseThrow(()-> new IllegalArgumentException("El id es invalido"));
        if (inmueble != null) {

            for (MultipartFile archivo : files) {
                String filePath = CARPETA_PATH + archivo.getOriginalFilename();

                Imagen imagen = new Imagen();
                imagen.setNombre(archivo.getOriginalFilename());
                imagen.setTipo(archivo.getContentType());
                imagen.setInmueble(inmueble);
                imagen.setFilePath(filePath);

                imagenRepo.save(imagen);
                try {
                    File file = new File(filePath);
                    archivo.transferTo(file);
                } catch (Exception e) {
                    return "Error al cargar las imagenes " + archivo.getOriginalFilename();
                }

            }
            return "Imagenes subidas correctamente";
        } else {
            return "No se encontro el ID de inmueble " + idInmueble;
        }
    }

    @Override
    public List<Imagen> listaImagenes(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Imagen> imagenPage = imagenRepo.findAll(pageable);
            List<Imagen> imagenList = imagenPage.getContent();
            return imagenList;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("No se pudo listar las imagenes");
        }
    }

    @Override
    public String eliminarIamgen(Integer id) {
        try{
            if(id == null){
                throw new IllegalArgumentException("Ingrese un Id valido");
            }
        Imagen imagen = imagenRepo.findById(id)
                .orElseThrow(()-> new NoSuchElementException("No se encontro la imagen"));
            imagenRepo.delete(imagen);
            return "Se ha eliminado la imagen";
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("No se pudo eliminar la imagen");
    }
    }

    @Override
    public void actualizar(Imagen imagen, Integer id) {
       try{
        if(id == null){
            throw new NoSuchElementException("Ingrese un Id valido");
        }
           Imagen imagenExistente = imagenRepo.findById(id)
                   .orElseThrow(()-> new IllegalArgumentException("No se encontró la imagen"));
            imagenExistente.setFilePath(imagen.getFilePath());
            imagenExistente.setTipo(imagen.getTipo());
            imagenExistente.setNombre(imagen.getNombre());
           imagenRepo.save(imagenExistente);
        }catch(Exception e){
           e.printStackTrace();
           throw new RuntimeException("No se pudo actualizar la imagen");
       }
    }
}
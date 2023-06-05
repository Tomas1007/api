package com.ejemplo.api.controlador;

import com.ejemplo.api.dto.InmueblesImagenesDTO;
import com.ejemplo.api.entidades.Imagen;

import com.ejemplo.api.entidades.Inmueble;
import com.ejemplo.api.repository.ImagenRepo;
import com.ejemplo.api.repository.InmuebleRepo;
import com.ejemplo.api.servicio.ImagenServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/imagen")
public class ImagenControlador {

    @Autowired
    private ImagenRepo imagenRepo;
    @Autowired
    private InmuebleRepo inmuebleRepo;
    @Autowired
    private ImagenServicio imagenServicio;

    @PostMapping
    public ResponseEntity<?> crearImagen(@RequestParam("files") MultipartFile file,
                                                @RequestParam("idInmueble")Integer idInmueble) throws IOException {
        String imagen = imagenServicio.subirImagen(file, idInmueble);
        return ResponseEntity.status(HttpStatus.OK)
                .body(imagen);

    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Imagen>> listar() {
        List<Imagen> imagen = imagenRepo.findAll();
        return new ResponseEntity<>(imagen, HttpStatus.OK);
    }



    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteImage(@PathVariable("id") Integer id) {
        Optional<Imagen> imagen = imagenRepo.findById(id);
        if (imagen.isPresent()) {
            imagenRepo.delete(imagen.get());
            return new ResponseEntity<>("Se ha eliminado la imagen", HttpStatus.OK);
        }
        return new ResponseEntity<>("Verifique el id", HttpStatus.NOT_FOUND);

    }
}
package com.ejemplo.api.controlador;

import com.ejemplo.api.repository.ImagenPortadaRepo;
import com.ejemplo.api.servicio.ImagenServicioImpl;
import com.ejemplo.api.servicio.PortadaServicioImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

@RestController
@RequestMapping("/api/portada/")
@RequiredArgsConstructor
public class ImagenPortadaAControlador {

    private final PortadaServicioImpl portadaServicio;

    @PostMapping
    public ResponseEntity<?> crearImagen(@RequestParam("file") MultipartFile file,
                                         @RequestParam("idInmueble")Integer idInmueble) throws IOException {

    return ResponseEntity.ok(portadaServicio.subirImagenPortada(file, idInmueble));
    }


}
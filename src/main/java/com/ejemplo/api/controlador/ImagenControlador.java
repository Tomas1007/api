package com.ejemplo.api.controlador;

import com.ejemplo.api.entidades.Imagen;

import com.ejemplo.api.repository.InmuebleRepo;
import com.ejemplo.api.servicio.ImagenServicioImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;



@RestController
@RequestMapping("/api/imagen")
@RequiredArgsConstructor
public class ImagenControlador {


    private final InmuebleRepo inmuebleRepo;

    private final ImagenServicioImpl imagenServicioImpl;

    @PostMapping
    public ResponseEntity<?> crearImagen(@RequestParam("files") MultipartFile[] files,
                                         @RequestParam("idInmueble")Integer idInmueble) throws IOException {
        String imagen = imagenServicioImpl.subirImagen(files, idInmueble);
        return ResponseEntity.status(HttpStatus.OK)
                .body(imagen);

    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Imagen>> listar( @RequestParam(value = "page",defaultValue = "0", required = false)int page,
                                                @RequestParam(value = "size",defaultValue = "10", required = false)int size) {
        return new ResponseEntity<>(imagenServicioImpl.listaImagenes(page, size), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteImage(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(imagenServicioImpl.eliminarIamgen(id), HttpStatus.OK);
    }
    @PutMapping("id")
    public ResponseEntity<Void> imagenUpd(@PathVariable("id") Integer id,
                                          @RequestBody Imagen imagen){
        imagenServicioImpl.actualizar(imagen, id);
        return ResponseEntity.noContent().build();
    }
}
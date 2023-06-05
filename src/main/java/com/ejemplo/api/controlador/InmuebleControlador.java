package com.ejemplo.api.controlador;


import com.ejemplo.api.dto.InmueblesImagenesDTO;
import com.ejemplo.api.entidades.Imagen;
import com.ejemplo.api.entidades.Inmueble;
import com.ejemplo.api.repository.InmuebleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inmueble")
public class InmuebleControlador {

    @Autowired
    private InmuebleRepo inmuebleRepo;

    @PostMapping
    public ResponseEntity<Inmueble> crearInmueble(@RequestBody Inmueble inmueble){
        return new ResponseEntity<>(inmuebleRepo.save(inmueble), HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Inmueble>> listar(){
        List<Inmueble> inmuebles = inmuebleRepo.findAll();
        return new ResponseEntity<>(inmuebles, HttpStatus.OK);
    }
    /*@GetMapping("/{id}")
    public ResponseEntity<Optional<Inmueble>> listarPorId(@PathVariable("id") Integer id){
        Optional<Inmueble> inmueble = inmuebleRepo.findById(id);
        return new ResponseEntity<>(inmueble, HttpStatus.OK);
    }*/
    @GetMapping("/{id}")
    public ResponseEntity<InmueblesImagenesDTO> obtenerInmuebleImagenes(@PathVariable("id") Integer id) {
        Inmueble inmueble = inmuebleRepo.findById(id).orElseThrow();
        if(inmueble != null){
            List<String> pathImagenes = inmueble.getImagenes()
                    .stream().map(Imagen::getFilePath).collect(Collectors.toList());
            InmueblesImagenesDTO inmueblesImagenesDTO = new InmueblesImagenesDTO(inmueble, pathImagenes);
            return ResponseEntity.ok(inmueblesImagenesDTO);
        }else {
            return ResponseEntity.notFound().build();
        }

    }
}

package com.ejemplo.api.controlador;


import com.ejemplo.api.dto.*;
import com.ejemplo.api.entidades.Comentario;
import com.ejemplo.api.entidades.Imagen;
import com.ejemplo.api.entidades.Inmueble;
import com.ejemplo.api.repository.ComentarioRepo;
import com.ejemplo.api.repository.InmuebleRepo;
import com.ejemplo.api.servicio.InmuebleServicioImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inmueble")
@RequiredArgsConstructor
public class InmuebleControlador {

    private final InmuebleRepo inmuebleRepo;

    private final InmuebleServicioImpl inmuebleServicioImpl;


    //Servicio para traer todos los Inmuebles sin sus comentarios
    @GetMapping("/sinComentarios")
    public ResponseEntity<List<InmuebleSinComentariosDto>>listarTodoSinComentarios(@RequestParam(defaultValue = "0")int page,
                                                          @RequestParam(defaultValue = "10")int size){
        List<InmuebleSinComentariosDto> inmuebles = inmuebleServicioImpl.listarTodoSinComentarios(page, size);
        return new ResponseEntity<>(inmuebles, HttpStatus.OK);
    }

    //Servicio para traer todos los Inmuebles incluyendo sus comentarios
    @GetMapping("/getAll")
    public ResponseEntity<List<InmuebleAllDto>>listarTodo(@RequestParam(defaultValue = "0")int page,
                                                                     @RequestParam(defaultValue = "10")int size){
        List<InmuebleAllDto> inmuebles = inmuebleServicioImpl.listarTodo(page, size);
        return new ResponseEntity<>(inmuebles, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<InmuebleAllDto>listarPorId(@RequestParam("id")Integer id) {

        return ResponseEntity.ok(inmuebleServicioImpl.listarPorId(id));
    }

    @GetMapping("/pileta")
    public ResponseEntity<List<InmuebleAllDto>> buscarPorPileta(@RequestParam(value = "page",defaultValue = "0", required = false)int page,
                                                                @RequestParam(value = "size",defaultValue = "10", required = false)int size){
        List<InmuebleAllDto> inmuebleAllDtoList = inmuebleServicioImpl.buscarPorPileta(page, size);
        return ResponseEntity.ok(inmuebleAllDtoList);
    }
    @GetMapping("/parrilla")
    public ResponseEntity<List<InmuebleAllDto>> buscarPorParrilla(@RequestParam(value = "page",defaultValue = "0", required = false)int page,
                                                                  @RequestParam(value = "size",defaultValue = "10", required = false)int size){
        List<InmuebleAllDto> inmuebleAllDtoList = inmuebleServicioImpl.buscarPorParrilla(page, size);
        return ResponseEntity.ok(inmuebleAllDtoList);
    }
    @GetMapping("/parrilla-pileta")
    public ResponseEntity<List<InmuebleAllDto>> buscarPorParrillaYPileta(@RequestParam(value = "page",defaultValue = "0", required = false)int page,
                                                                         @RequestParam(value = "size",defaultValue = "10", required = false)int size){
        List<InmuebleAllDto> inmuebleAllDtoList = inmuebleServicioImpl.buscarPorParrillaYPileta(page, size);
        return ResponseEntity.ok(inmuebleAllDtoList);
    }
    @GetMapping("/{id}")
    public ResponseEntity<InmueblesImagenesDTO> obtenerInmuebleImagenes(@PathVariable("id") Integer id) {
        try {
            if (id == null) {
                throw new IllegalArgumentException("Ingrese un Id valido");
            }
            Inmueble inmueble = inmuebleRepo.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("No se ah encontrado el inmueble"));
            List<String> pathImagenes = inmueble.getImagenes()
                    .stream().map(Imagen::getFilePath).collect(Collectors.toList());
            InmueblesImagenesDTO inmueblesImagenesDTO = new InmueblesImagenesDTO(inmueble, pathImagenes);
            return ResponseEntity.ok(inmueblesImagenesDTO);
        }catch(Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping("/user")
    public ResponseEntity<?> crearInmueble(@RequestBody InmuebleGuardarDto inmuebleGuardarDto,
                                           @RequestParam("email") String email){
        return new ResponseEntity<>(inmuebleServicioImpl.guardar(inmuebleGuardarDto, email), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InmuebleUpdDto> actualizar(@PathVariable("id")Integer id, @RequestBody Inmueble inmueble){
         InmuebleUpdDto usuarioUpd = inmuebleServicioImpl.actualizar(id, inmueble);
        return ResponseEntity.ok(usuarioUpd);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar (@PathVariable("id")Integer id){
        inmuebleServicioImpl.eliminar(id);
       return ResponseEntity.noContent().build();
    }
}

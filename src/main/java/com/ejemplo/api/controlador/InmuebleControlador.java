package com.ejemplo.api.controlador;


import com.ejemplo.api.dto.*;

import com.ejemplo.api.entidades.Inmueble;

import com.ejemplo.api.repository.InmuebleRepo;
import com.ejemplo.api.servicio.InmuebleServicioImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



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
    public ResponseEntity<List<InmuebleWithPortada>>listarTodo(@RequestParam(defaultValue = "0")int page,
                                                                     @RequestParam(defaultValue = "10")int size){
        List<InmuebleWithPortada> inmuebles = inmuebleServicioImpl.listarTodo(page, size);
        return new ResponseEntity<>(inmuebles, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<InmuebleAllDto>listarPorId(@PathVariable("id") Integer id) {

        return ResponseEntity.ok(inmuebleServicioImpl.listarPorId(id));
    }


    @GetMapping("/user/{email}")
    public ResponseEntity<List<InmuebleWithPortada>> buscarPorUserEmail(@PathVariable("email") String email,
                                                                    @RequestParam(value = "page",defaultValue = "0", required = false)int page,
                                                                   @RequestParam(value = "size",defaultValue = "10", required = false)int size){
        List<InmuebleWithPortada> inmuebleAllDtoList = inmuebleServicioImpl.buscarPorUserEmail(email, page, size);
        return ResponseEntity.ok(inmuebleAllDtoList);
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

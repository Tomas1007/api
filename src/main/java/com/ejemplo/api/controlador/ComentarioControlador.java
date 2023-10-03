package com.ejemplo.api.controlador;

import com.ejemplo.api.dto.ComentarioDto;
import com.ejemplo.api.dto.ComentarioGuardarDto;
import com.ejemplo.api.dto.ComentarioListDto;
import com.ejemplo.api.dto.ComentarioUpd;
import com.ejemplo.api.servicio.ComentarioServicioImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comentario")
@RequiredArgsConstructor
public class ComentarioControlador {

    private final ComentarioServicioImpl comentarioServicio;

    @GetMapping("/inmueble/{id}")
    public ResponseEntity<ComentarioListDto> listarComentario(@PathVariable("id") Integer inmuebleId,
                                                              @RequestParam(value = "page",defaultValue = "0", required = false)int page,
                                                              @RequestParam(value = "size",defaultValue = "10", required = false)int size){
        return ResponseEntity.ok(comentarioServicio.listarComentarios(inmuebleId, page, size));
    }
    @PutMapping("/user/inmueble")
    public ResponseEntity<ComentarioDto> comentarioUpd(@RequestParam("email") String email,
                                                        @RequestParam("inmuebleId")Integer inmuebleId,
                                                    @RequestBody ComentarioUpd comentarioUpd){
        return new ResponseEntity<>(comentarioServicio.actualizarComentario(email, inmuebleId, comentarioUpd), HttpStatus.OK);
    }

    @PostMapping("/user/inmueble")
    public ResponseEntity<ComentarioDto> crearComentario(@RequestBody ComentarioGuardarDto comentarioGuardar,
                                                         @RequestParam("email")String email,
                                                         @RequestParam("inmuebleId")Integer inmuebleId){
        return ResponseEntity.ok(comentarioServicio.crearComentario(comentarioGuardar, email, inmuebleId));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarComentario(@PathVariable Integer id){
        comentarioServicio.eliminarComentario(id);
        return ResponseEntity.noContent().build();
    }

}

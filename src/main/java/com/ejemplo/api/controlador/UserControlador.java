package com.ejemplo.api.controlador;

import com.ejemplo.api.dto.UserListDto;
import com.ejemplo.api.dto.UserUpdDto;
import com.ejemplo.api.servicio.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserControlador {

    private final UserServiceImpl userServiceImpl;

    @GetMapping("/getAll")
    public ResponseEntity<List<UserListDto>> listarUsuarios(@RequestParam(value = "page",defaultValue = "0", required = false)int page,
                                                            @RequestParam(value = "size",defaultValue = "10", required = false)int size){
        List<UserListDto> userListDto = userServiceImpl.listarUsers(page, size);
        return ResponseEntity.ok(userListDto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserListDto> listarUsuarioPorId(@PathVariable("id")Integer id){
        return ResponseEntity.ok(userServiceImpl.buscarUserPorId(id));
    }
    @PutMapping
    public ResponseEntity<UserUpdDto> actualizarUserio(@PathVariable("id")Integer id,
                                                     @RequestBody UserUpdDto userUpdDto){
        return ResponseEntity.ok( userServiceImpl.actualizarUser(id, userUpdDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUserio(@PathVariable("id")Integer id){
        userServiceImpl.eliminarUser(id);
        return ResponseEntity.noContent().build();
    }

}

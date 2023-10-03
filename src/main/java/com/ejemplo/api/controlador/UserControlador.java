package com.ejemplo.api.controlador;

import com.ejemplo.api.dto.UserListDto;
import com.ejemplo.api.dto.UserUpdDto;
import com.ejemplo.api.servicio.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserControlador {

    private final UserServiceImpl userServiceImpl;

    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UserListDto>> listarUsuarios(@RequestParam(value = "page",defaultValue = "0", required = false)int page,
                                                            @RequestParam(value = "size",defaultValue = "10", required = false)int size){
        List<UserListDto> userListDto = userServiceImpl.listarUsers(page, size);
        return ResponseEntity.ok(userListDto);
    }
    @GetMapping("/{email}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserListDto> listarUsuarioPorEmail(@PathVariable("email")String email){
        return ResponseEntity.ok(userServiceImpl.buscarUserPorEmail(email));
    }
    @PutMapping("/{email}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<UserUpdDto> actualizarUser(@PathVariable("email")String email,
                                                     @RequestBody UserUpdDto userUpdDto){
        return ResponseEntity.ok( userServiceImpl.actualizarUser(email, userUpdDto));
    }
    @PutMapping("/{email}/rol/{idRol}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<UserListDto> actualizarUserRol(@RequestParam("email")String email,
                                                           @RequestParam("idRol")Integer idRol){
        return ResponseEntity.ok( userServiceImpl.actualizarUserRol(email,idRol));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> eliminarUser(@PathVariable("id")Integer id){
        userServiceImpl.eliminarUser(id);
        return ResponseEntity.noContent().build();
    }

}

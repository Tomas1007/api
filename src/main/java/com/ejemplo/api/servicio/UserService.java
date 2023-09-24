package com.ejemplo.api.servicio;

import com.ejemplo.api.dto.UserListDto;
import com.ejemplo.api.dto.UserUpdDto;

import java.util.List;


public interface UserService {

    UserUpdDto actualizarUser (Integer id, UserUpdDto userUpdDto);

    void eliminarUser(Integer id);

    List<UserListDto> listarUsers(int page , int size);

    UserListDto buscarUserPorId(Integer id);
}

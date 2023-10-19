package com.ejemplo.api.servicio;

import com.ejemplo.api.dto.UserListDto;
import com.ejemplo.api.dto.UserUpdDto;
import com.ejemplo.api.entidades.User;

import java.util.List;


public interface UserService {

    UserUpdDto actualizarUser (String email, User user);

    void eliminarUser(Integer id);

    List<UserListDto> listarUsers(int page , int size);

    UserListDto buscarUserPorEmail(String email);
}

package com.ejemplo.api.servicio;


import com.ejemplo.api.dto.UserListDto;
import com.ejemplo.api.dto.UserUpdDto;
import com.ejemplo.api.entidades.Rol;
import com.ejemplo.api.entidades.User;
import com.ejemplo.api.repository.RolRepository;
import com.ejemplo.api.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RolRepository rolRepository;
    @Override
    public UserUpdDto actualizarUser(String email, User user) {
        try{
            if(email.isEmpty()){
                throw new IllegalArgumentException("Ingrese un id valido");
            }
            User userExistente = userRepository.findByEmail(email)
                    .orElseThrow(()-> new NoSuchElementException("No existe el usuario"));
            userExistente.setName(user.getName());
            userExistente.setLastName(user.getLastName());
            userExistente.setEmail(user.getEmail());
            userExistente.setPhoneNumber(user.getPhoneNumber());

            userRepository.save(userExistente);
            return new UserUpdDto(
                    userExistente.getName(),
                    userExistente.getLastName(),
                    userExistente.getEmail(),
                    userExistente.getPhoneNumber());

        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("No se púdo actualizar los campos");
        }
    }

    @Override
    public void eliminarUser(Integer id) {
        try{
            if(id == null){
                throw new IllegalArgumentException("Ingrese un id valido");
            }
            User user = userRepository.findById(id)
                    .orElseThrow(()-> new NoSuchElementException("No se ah encontrado el usuario"));
            user.getRoles().clear();
            userRepository.save(user);
            userRepository.delete(user);
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("No se pudo eliminar el usuario");
        }
    }

    @Override
    public List<UserListDto> listarUsers(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<User> users = userRepository.findAll(pageable);
                    return users.stream().map(
                    user -> new UserListDto(user.getId(),
                            user.getName(),
                            user.getLastName(),
                            user.getEmail(),
                            user.getPhoneNumber(),
                            user.getRoles().stream().map(Rol::getNombreRol).collect(Collectors.toList()))
            ).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("No se puede listar los usuarios");
        }

    }

    @Override
    public UserListDto buscarUserPorEmail(String email) {
        try{
            if(email.isEmpty()){
                throw new IllegalArgumentException("Ingrese un id valido");
            }
            User user = userRepository.findByEmail(email)
                    .orElseThrow(()-> new NoSuchElementException("No se ah encontrado el usuario"));
            return new UserListDto(
                    user.getId(),
                    user.getName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getPhoneNumber(),
                    user.getRoles().stream()
                            .map(Rol::getNombreRol).collect(Collectors.toList())
            );
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("No se pudo listar los datos del usuario");
        }
    }
    @Transactional
    public UserListDto actualizarUserRol (String email, Integer idRol){
        if(email.isEmpty()){
            throw new IllegalArgumentException("Rol no encontrado");
        }
        Rol rolRepo = rolRepository.findById(idRol)
                .orElseThrow(()-> new NoSuchElementException("Rol no encontrado"));
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new NoSuchElementException("Id rol no encontrado"));
        user.getRoles().add(rolRepo);
        var userSave = userRepository.save(user);
        return new UserListDto(
                userSave.getId(),
                userSave.getName(),
                userSave.getLastName(),
                userSave.getEmail(),
                userSave.getPhoneNumber(),
                userSave.getRoles().stream().map(Rol::getNombreRol).collect(Collectors.toList())
        );
    }

}

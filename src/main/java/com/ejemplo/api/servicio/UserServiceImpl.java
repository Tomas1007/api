package com.ejemplo.api.servicio;


import com.ejemplo.api.dto.UserListDto;
import com.ejemplo.api.dto.UserUpdDto;
import com.ejemplo.api.entidades.Rol;
import com.ejemplo.api.entidades.User;
import com.ejemplo.api.repository.UserRepository;
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
    @Override
    public UserUpdDto actualizarUser(Integer id, UserUpdDto userUpdDto) {
        try{
            if(id == null){
                throw new IllegalArgumentException("Ingrese un id valido");
            }
            User user = userRepository.findById(id)
                    .orElseThrow(()-> new NoSuchElementException("No existe el usuario"));
            user.setName(userUpdDto.name());
            user.setLastName(userUpdDto.lastname());
            user.setEmail(userUpdDto.email());

            userRepository.save(user);
            return new UserUpdDto(userUpdDto.name(),
                    userUpdDto.lastname(),
                    userUpdDto.email());

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
                            user.getRoles().stream().map(Rol::getNombreRol).collect(Collectors.toList()))
            ).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("No se puede listar los usuarios");
        }

    }

    @Override
    public UserListDto buscarUserPorId(Integer id) {
        try{
            if(id == null){
                throw new IllegalArgumentException("Ingrese un id valido");
            }
            User user = userRepository.findById(id)
                    .orElseThrow(()-> new NoSuchElementException("No se ah encontrado el usuario"));
            return new UserListDto(
                    user.getId(),
                    user.getName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getRoles().stream()
                            .map(Rol::getNombreRol).collect(Collectors.toList())
            );
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("No se pudo listar los datos del usuario");
        }
    }
}
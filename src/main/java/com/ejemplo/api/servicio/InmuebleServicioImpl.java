package com.ejemplo.api.servicio;

import com.ejemplo.api.dto.*;
import com.ejemplo.api.entidades.Comentario;
import com.ejemplo.api.entidades.Imagen;
import com.ejemplo.api.entidades.Inmueble;
import com.ejemplo.api.entidades.User;
import com.ejemplo.api.repository.InmuebleRepo;
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
public class InmuebleServicioImpl implements InmuebleServicio{


    private final InmuebleRepo inmuebleRepo;

    private final UserRepository userRepository;

    @Override
    public InmuebleDto guardar(InmuebleGuardarDto inmuebleGuardarDto, Integer userId) {
        try{
        if (userId == null) {
            throw new IllegalArgumentException("Ingrese un id valido");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException(("No se ha encontrado el usuario ")));
        Inmueble inmueble = new Inmueble();
        inmueble.setDescripcion(inmuebleGuardarDto.descripcion());
        inmueble.setNombre(inmuebleGuardarDto.nombre());
        inmueble.setFechaCreacion(inmuebleGuardarDto.fechaCreacion());
        inmueble.setPileta(inmuebleGuardarDto.pileta());
        inmueble.setParrilla(inmuebleGuardarDto.parrilla());
        inmueble.setUser(user);
        UserDto userDto = new UserDto(
                user.getName(),
                user.getLastName(),
                user.getEmail()
        );
        inmuebleRepo.save(inmueble);
            return new InmuebleDto(
                    inmueble.getNombre(),
                    inmueble.getDescripcion(),
                    inmueble.getFechaCreacion(),
                    inmueble.isPileta(),
                    inmueble.isParrilla(),
                    userDto
            );
    }catch(Exception e){
        e.printStackTrace();
        throw new RuntimeException("No se pudo crear el inmueble");
    }

    }
    @Override
    public List<InmuebleAllDto> listarTodo(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Inmueble> inmuebles = inmuebleRepo.findAll(pageable);

            return inmuebles.getContent()
                     .stream()
                     .map(i -> new InmuebleAllDto(
                             i.getId(),
                             i.getNombre(),
                             i.getDescripcion(),
                             i.getFechaCreacion(),
                             i.isPileta(),
                             i.isParrilla(),
                             i.getImagenes().stream().map(Imagen::getFilePath).collect(Collectors.toList()),
                             i.getComentarios().stream().map(Comentario::getContenido).collect(Collectors.toList()),
                             i.getUser().getName()
                     )).toList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("No se pudo listar los inmuebles");
        }
    }

    @Override()
    public InmuebleUpdDto actualizar(Integer id, Inmueble inmueble){
        try {
            if (id == null) {
                throw new IllegalArgumentException("Ingrese un Id valido");
            }
            Inmueble inmuebleExistente = inmuebleRepo.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("No se ha encontrado el inmueble"));
            inmuebleExistente.setDescripcion(inmueble.getDescripcion());

            UserDto userDto = new UserDto(inmuebleExistente.getUser().getName(),
                    inmuebleExistente.getUser().getLastName(),
                    inmuebleExistente.getUser().getEmail());
            InmuebleUpdDto inmuebleUpdDto = new InmuebleUpdDto(inmuebleExistente.getNombre(),
                    inmuebleExistente.getDescripcion(),
                    inmuebleExistente.isPileta(),
                    inmuebleExistente.isParrilla(),
                    userDto);
            inmuebleRepo.save(inmuebleExistente);
            return inmuebleUpdDto;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("No se pudo actualizar el inmueble");
        }

    }
    @Override
    public void eliminar(Integer id) {
        try{
            if(id == null){
                throw new IllegalArgumentException("Ingrese un Id valido");
            }
        Inmueble inmueble = inmuebleRepo.findById(id)
                .orElseThrow(()-> new NoSuchElementException("No se encontro el inmueble"));
            inmuebleRepo.delete(inmueble);
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("No se pudo eliminar el inmueble");
        }
    }
    public List<InmuebleAllDto> buscarPorPileta(boolean pileta, int page,int size){
        try{
            if(!pileta){
               throw new IllegalArgumentException("No se encontro inmuebles con pileta");
            }
            Pageable pageable = PageRequest.of(page, size);
            Page<Inmueble> inmuebles = inmuebleRepo.findByPileta(pileta, pageable);

            return inmuebles.getContent().stream()
                    .map(i -> new InmuebleAllDto(
                            i.getId(),
                            i.getNombre(),
                            i.getDescripcion(),
                            i.getFechaCreacion(),
                            i.isPileta(),
                            i.isParrilla(),
                            i.getImagenes().stream()
                                    .map(Imagen::getFilePath).collect(Collectors.toList()),
                            i.getComentarios().stream()
                                    .map(Comentario::getContenido).collect(Collectors.toList()),
                            i.getUser().getName())).toList();

        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("No se pudo listar inmuebles con pileta");
        }
    }
    public List<InmuebleAllDto> buscarPorParrilla(boolean parrilla, int page, int size) {
        try {
            if (!parrilla) {
                throw new IllegalArgumentException("No se encontro inmuebles con parrilla");
            }
            Pageable pageable = PageRequest.of(page, size);
            Page<Inmueble> inmuebles = inmuebleRepo.findByParrilla(parrilla, pageable);

            return inmuebles.getContent()
                    .stream()
                    .map(i -> new InmuebleAllDto(
                            i.getId(),
                            i.getNombre(),
                            i.getDescripcion(),
                            i.getFechaCreacion(),
                            i.isPileta(),
                            i.isParrilla(),
                            i.getImagenes().stream()
                                    .map(Imagen::getFilePath).collect(Collectors.toList()),
                            i.getComentarios().stream()
                                    .map(Comentario::getContenido).collect(Collectors.toList()),
                            i.getUser().getName())).toList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("No se pudo listar inmuebles con parrilla");
        }
    }

}
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
    public InmuebleAllDto listarPorId(Integer id) {
        try {
            if (id == null) {
                throw new IllegalArgumentException("Ingrese un id valido");
            }
            Inmueble inmueble = inmuebleRepo.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("No se encontro el inmueble"));
            return new InmuebleAllDto(
                    inmueble.getId(),
                    inmueble.getTitulo(),
                    inmueble.getDescripcion(),
                    inmueble.getFechaCreacion(),
                    inmueble.getPrecio(),
                    inmueble.getLocalidad(),
                    inmueble.getUbicacion(),
                    inmueble.isPileta(),
                    inmueble.isParrilla(),
                    inmueble.getImagenes().stream().map(Imagen::getFilePath).collect(Collectors.toList()),
                    inmueble.getComentarios().stream().map(Comentario::getContenido).collect(Collectors.toList()),
                    inmueble.getUser().getName()
            );
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("No se pudo buscar por ID");
        }
    }

    @Override
    public InmuebleDto guardar(InmuebleGuardarDto inmuebleGuardarDto, String email) {
        try{
        if (email.isEmpty()) {
            throw new IllegalArgumentException("Ingrese un id valido");
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException(("No se ha encontrado el usuario ")));
        Inmueble inmueble = new Inmueble();
        inmueble.setDescripcion(inmuebleGuardarDto.descripcion());
        inmueble.setTitulo(inmuebleGuardarDto.titulo());
        inmueble.setFechaCreacion(inmuebleGuardarDto.fechaCreacion());
        inmueble.setPrecio(inmuebleGuardarDto.precio());
        inmueble.setPileta(inmuebleGuardarDto.pileta());
        inmueble.setParrilla(inmuebleGuardarDto.parrilla());
        inmueble.setLocalidad(inmuebleGuardarDto.localidad());
        inmueble.setUbicacion(inmuebleGuardarDto.ubicacion());
        inmueble.setUser(user);
        UserDto userDto = new UserDto(
                user.getName(),
                user.getLastName(),
                user.getEmail()
        );
        inmuebleRepo.save(inmueble);
            return new InmuebleDto(
                    inmueble.getTitulo(),
                    inmueble.getDescripcion(),
                    inmueble.getFechaCreacion(),
                    inmueble.getPrecio(),
                    inmueble.getLocalidad(),
                    inmueble.getUbicacion(),
                    inmueble.isPileta(),
                    inmueble.isParrilla(),
                    userDto
            );
    }catch(Exception e){
        e.printStackTrace();
        throw new RuntimeException("No se pudo crear el inmueble");
    }

    }

    public List<InmuebleAllDto> listarTodo(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Inmueble> inmuebles = inmuebleRepo.findAll(pageable);

            return inmuebles.getContent()
                    .stream()
                    .map(i -> new InmuebleAllDto(
                            i.getId(),
                            i.getTitulo(),
                            i.getDescripcion(),
                            i.getFechaCreacion(),
                            i.getPrecio(),
                            i.getLocalidad(),
                            i.getUbicacion(),
                            i.isPileta(),
                            i.isParrilla(),
                            i.getImagenes().stream().map(Imagen::getFilePath).collect(Collectors.toList()),
                            i.getComentarios().stream().map(Comentario::getContenido).toList(),
                            i.getUser().getName()
                    )).toList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("No se pudo listar los inmuebles");
        }
    }

    @Override
    public List<InmuebleSinComentariosDto> listarTodoSinComentarios(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Inmueble> inmuebles = inmuebleRepo.findAll(pageable);

            return inmuebles.getContent()
                     .stream()
                     .map(i -> new InmuebleSinComentariosDto(
                             i.getId(),
                             i.getTitulo(),
                             i.getDescripcion(),
                             i.getFechaCreacion(),
                             i.getPrecio(),
                             i.getLocalidad(),
                             i.getUbicacion(),
                             i.isPileta(),
                             i.isParrilla(),
                             i.getImagenes().stream().map(Imagen::getFilePath).collect(Collectors.toList()),
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
            inmuebleExistente.setTitulo(inmueble.getTitulo());
            inmuebleExistente.setDescripcion(inmueble.getDescripcion());
            inmuebleExistente.setPrecio(inmueble.getPrecio());
            inmuebleExistente.setLocalidad(inmueble.getLocalidad());
            inmuebleExistente.setUbicacion(inmueble.getUbicacion());

            UserDto userDto = new UserDto(inmuebleExistente.getUser().getName(),
                    inmuebleExistente.getUser().getLastName(),
                    inmuebleExistente.getUser().getEmail());
            InmuebleUpdDto inmuebleUpdDto = new InmuebleUpdDto(inmuebleExistente.getTitulo(),
                    inmuebleExistente.getDescripcion(),
                    inmuebleExistente.getLocalidad(),
                    inmuebleExistente.getUbicacion(),
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
  //  public

    public List<InmuebleAllDto> buscarPorPileta(int page, int size){
        try{

            Pageable pageable = PageRequest.of(page, size);
            Page<Inmueble> inmuebles = inmuebleRepo.findByPileta(true, pageable);

            return inmuebles.getContent().stream()
                    .map(i -> new InmuebleAllDto(
                            i.getId(),
                            i.getTitulo(),
                            i.getDescripcion(),
                            i.getFechaCreacion(),
                            i.getPrecio(),
                            i.getLocalidad(),
                            i.getUbicacion(),
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
    public List<InmuebleAllDto> buscarPorParrilla(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Inmueble> inmuebles = inmuebleRepo.findByParrilla(true, pageable);

            return inmuebles.getContent()
                    .stream()
                    .map(i -> new InmuebleAllDto(
                            i.getId(),
                            i.getTitulo(),
                            i.getDescripcion(),
                            i.getFechaCreacion(),
                            i.getPrecio(),
                            i.getLocalidad(),
                            i.getUbicacion(),
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
    public List<InmuebleAllDto> buscarPorParrillaYPileta(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Inmueble> inmuebles = inmuebleRepo.findByParrillaAndPileta(true,true, pageable);

            return inmuebles.getContent()
                    .stream()
                    .map(i -> new InmuebleAllDto(
                            i.getId(),
                            i.getTitulo(),
                            i.getDescripcion(),
                            i.getFechaCreacion(),
                            i.getPrecio(),
                            i.getLocalidad(),
                            i.getUbicacion(),
                            i.isPileta(),
                            i.isParrilla(),
                            i.getImagenes().stream()
                                    .map(Imagen::getFilePath).collect(Collectors.toList()),
                            i.getComentarios().stream()
                                    .map(Comentario::getContenido).collect(Collectors.toList()),
                            i.getUser().getName())).toList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("No se pudo listar inmuebles con parrilla y pileta");
        }
    }
}
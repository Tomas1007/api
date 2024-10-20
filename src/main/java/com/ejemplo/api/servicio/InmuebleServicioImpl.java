package com.ejemplo.api.servicio;

import com.ejemplo.api.dto.*;
import com.ejemplo.api.entidades.*;
import com.ejemplo.api.repository.CaracteristicasRepo;
import com.ejemplo.api.repository.InmuebleRepo;
import com.ejemplo.api.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InmuebleServicioImpl implements InmuebleServicio{


    private final InmuebleRepo inmuebleRepo;

    private final CaracteristicasRepo caracteristicasRepo;
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
                    inmueble.getImagenes().stream().map(Imagen::getFilePath).collect(Collectors.toList()),
                    inmueble.getComentarios().stream().map(Comentario::getContenido).collect(Collectors.toList()),
                    inmueble.getUser().getName()
            );
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("No se pudo buscar por ID");
        }
    }

    public List<InmuebleConPiscina> listarByPiscina () {

        try {


            List<Inmueble> inmueble = inmuebleRepo.findByCaracteristicasPiscinaIsTrue();

           return inmueble.stream().map(i -> new InmuebleConPiscina(
                   i.getId(),
                   i.getTitulo(),
                   i.getDescripcion(),
                   i.getFechaCreacion(),
                   i.getPrecio(),
                   i.getLocalidad(),
                   i.getUbicacion(),
                   Optional.ofNullable(i.getImagenPortada())
                           .map(ImagenPortada::getFilePath)
                           .orElse(null),
                   i.getComentarios().stream().map(Comentario::getContenido).toList(),
                   Optional.ofNullable(i.getCaracteristicas()).map(Caracteristicas::getHabitaciones).orElse(null),
                   Optional.ofNullable(i.getCaracteristicas()).map(Caracteristicas::getCantidadPersonas).orElse(null),
                   i.getUser().getName(),
                   Optional.ofNullable(i.getCaracteristicas()).map(Caracteristicas::isPiscina).orElse(null)
           )).toList();

        } catch (Exception e) {

            e.printStackTrace();
            throw new RuntimeException("No se pudo encontrar inmuebles con piscina");
        }
    }
    public List<InmuebleConParrillaYPiscina> listarPorInmuebleYParrilla() {

        try {

            List<Inmueble> inmueble = inmuebleRepo.findByCaracteristicas_ParrillaTrueAndCaracteristicas_PiscinaTrue();

            return inmueble.stream().map(i -> new InmuebleConParrillaYPiscina(
                    i.getId(),
                    i.getTitulo(),
                    i.getDescripcion(),
                    i.getFechaCreacion(),
                    i.getPrecio(),
                    i.getLocalidad(),
                    i.getUbicacion(),
                    Optional.ofNullable(i.getImagenPortada())
                            .map(ImagenPortada::getFilePath)
                            .orElse(null),
                    i.getComentarios().stream().map(Comentario::getContenido).toList(),
                    Optional.ofNullable(i.getCaracteristicas()).map(Caracteristicas::getHabitaciones).orElse(null),
                    Optional.ofNullable(i.getCaracteristicas()).map(Caracteristicas::getCantidadPersonas).orElse(null),
                    i.getUser().getName(),
                    Optional.ofNullable(i.getCaracteristicas()).map(Caracteristicas::isPiscina).orElse(null),
                    Optional.ofNullable(i.getCaracteristicas()).map(Caracteristicas::isParrilla).orElse(null)

            )).toList();

        } catch (Exception e) {

            e.printStackTrace();
            throw new RuntimeException("No se pudo encontrar inmuebles con piscina");
        }
    }



    public List<InmuebleConParrilla> listarByParrilla() {

        try {

            List<Inmueble> inmueble = inmuebleRepo.findByCaracteristicasParrillaIsTrue();

            return inmueble.stream().map(i -> new InmuebleConParrilla(
                    i.getId(),
                    i.getTitulo(),
                    i.getDescripcion(),
                    i.getFechaCreacion(),
                    i.getPrecio(),
                    i.getLocalidad(),
                    i.getUbicacion(),
                    Optional.ofNullable(i.getImagenPortada())
                            .map(ImagenPortada::getFilePath)
                            .orElse(null),
                    i.getComentarios().stream().map(Comentario::getContenido).toList(),
                    Optional.ofNullable(i.getCaracteristicas()).map(Caracteristicas::getHabitaciones).orElse(null),
                    Optional.ofNullable(i.getCaracteristicas()).map(Caracteristicas::getCantidadPersonas).orElse(null),
                    i.getUser().getName(),
                    Optional.ofNullable(i.getCaracteristicas()).map(Caracteristicas::isParrilla).orElse(null)
            )).toList();

        } catch (Exception e) {

            e.printStackTrace();
            throw new RuntimeException("No se pudo encontrar inmuebles con piscina");
        }
    }


    @Override
    public InmuebleDto guardar(InmuebleGuardarDto inmuebleGuardarDto, String email) {
        try{
        if (email.isEmpty()) {
            throw new IllegalArgumentException("Ingrese un email valido");
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException(("No se ha encontrado el usuario ")));
        Inmueble inmueble = new Inmueble();
        inmueble.setDescripcion(inmuebleGuardarDto.descripcion());
        inmueble.setTitulo(inmuebleGuardarDto.titulo());
        inmueble.setFechaCreacion(inmuebleGuardarDto.fechaCreacion());
        inmueble.setPrecio(inmuebleGuardarDto.precio());

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
                    inmueble.getId(),
                    inmueble.getTitulo(),
                    inmueble.getDescripcion(),
                    inmueble.getFechaCreacion(),
                    inmueble.getPrecio(),
                    inmueble.getLocalidad(),
                    inmueble.getUbicacion(),

                    userDto
            );
    }catch(Exception e){
        e.printStackTrace();
        throw new RuntimeException("No se pudo crear el inmueble");
    }

    }



    public List<InmuebleWithPortada> listarTodo(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Inmueble> inmuebles = inmuebleRepo.findAll(pageable);


            return inmuebles.getContent()
                    .stream()
                    .map(i -> new InmuebleWithPortada(
                            i.getId(),
                            i.getTitulo(),
                            i.getDescripcion(),
                            i.getFechaCreacion(),
                            i.getPrecio(),
                            i.getLocalidad(),
                            i.getUbicacion(),
                            Optional.ofNullable(i.getImagenPortada())
                            .map(ImagenPortada::getFilePath)
                                    .orElse(null),
                            i.getComentarios().stream().map(Comentario::getContenido).toList(),
                            Optional.ofNullable(i.getCaracteristicas()).map(Caracteristicas::getHabitaciones).orElse(null),
                            Optional.ofNullable(i.getCaracteristicas()).map(Caracteristicas::getCantidadPersonas).orElse(null),
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
    public List<InmuebleWithPortada> buscarPorUserEmail(String email,int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Inmueble> inmuebles = inmuebleRepo.findByUserEmail(email, pageable);

            return inmuebles.getContent()
                    .stream()
                    .map(i -> new InmuebleWithPortada(
                            i.getId(),
                            i.getTitulo(),
                            i.getDescripcion(),
                            i.getFechaCreacion(),
                            i.getPrecio(),
                            i.getLocalidad(),
                            i.getUbicacion(),
                            Optional.ofNullable(i.getImagenPortada())
                                    .map(ImagenPortada::getFilePath)
                                    .orElse(null),
                            i.getComentarios().stream().map(Comentario::getContenido).toList(),
                            i.getCaracteristicas().getHabitaciones(),
                            i.getCaracteristicas().getCantidadPersonas(),
                            i.getUser().getName())).toList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("No se pudo listar inmuebles con parrilla");
        }
    }


}
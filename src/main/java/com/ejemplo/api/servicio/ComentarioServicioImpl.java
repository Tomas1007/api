package com.ejemplo.api.servicio;

import com.ejemplo.api.dto.ComentarioDto;
import com.ejemplo.api.dto.ComentarioGuardarDto;
import com.ejemplo.api.dto.ComentarioListDto;
import com.ejemplo.api.dto.ComentarioUpd;
import com.ejemplo.api.entidades.Comentario;
import com.ejemplo.api.entidades.Imagen;
import com.ejemplo.api.entidades.Inmueble;
import com.ejemplo.api.entidades.User;
import com.ejemplo.api.repository.ComentarioRepo;
import com.ejemplo.api.repository.InmuebleRepo;
import com.ejemplo.api.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComentarioServicioImpl implements ComentarioServicio{

    @Autowired
    private final ComentarioRepo comentarioRepo;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final InmuebleRepo inmuebleRepo;

    @Transactional
    @Override
    public ComentarioDto crearComentario(ComentarioGuardarDto comentarioGuardarDto, Integer userId, Integer inmuebleId) {
       try {

           if (userId == null || inmuebleId == null) {
               throw new IllegalArgumentException("Usuario e inmueble invalidos");
           }
           User user = userRepository.findById(userId)
                   .orElseThrow(() -> new NoSuchElementException("No se ha encontrado un usuario valido"));
           Inmueble inmueble = inmuebleRepo.findById(inmuebleId)
                   .orElseThrow(() -> new NoSuchElementException("No se ha encontrado un inmueble valido"));

           Comentario comentario = new Comentario();
           comentario.setUser(user);
           comentario.setInmueble(inmueble);
           comentario.setContenido(comentarioGuardarDto.contenido());
           comentario.setFechaCreacion(comentarioGuardarDto.fechaCreacion());

           ComentarioDto comentarioDto = new ComentarioDto(
                   inmueble.getNombre(),
                   user.getName(),
                   comentario.getContenido(),
                   comentario.getFechaCreacion());

           comentarioRepo.save(comentario);
           return comentarioDto;
       }catch(Exception e){
           e.printStackTrace();
       }
       return null;
    }

    @Override
    public ComentarioDto actualizarComentario(Integer idUser, Integer inmuebleId, ComentarioUpd comentarioUpd) {
       try {

           if (idUser == null || inmuebleId == null) {
               throw new IllegalArgumentException("Ingrese valores reales");
           }
           User user = userRepository.findById(idUser)
                   .orElseThrow(() -> new NoSuchElementException("No se ha encontrado un usuario valido"));
           Inmueble inmueble = inmuebleRepo.findById(inmuebleId)
                   .orElseThrow(() -> new NoSuchElementException("No se ha encontrado un inmueble valido"));

           Comentario comentarioExistente = comentarioRepo.findById(comentarioUpd.id())
                   .orElseThrow(() -> new NoSuchElementException("No se ah encontrado un comentario valido"));
           comentarioExistente.setContenido(comentarioUpd.contenido());

           ComentarioDto comentarioDto = new ComentarioDto(
                   inmueble.getNombre(),
                   user.getName(),
                   comentarioExistente.getContenido(),
                   comentarioExistente.getFechaCreacion());
           comentarioRepo.save(comentarioExistente);
           return comentarioDto;
       }catch(Exception e){
           e.printStackTrace();
       }
       return null;
    }

    @Override
    public void eliminarComentario(Integer idComentario) {
       try{
        Optional<Comentario> comentarioOpt = comentarioRepo.findById(idComentario);
        if(comentarioOpt.isPresent()) {
            Comentario comentario = comentarioOpt.get();
            comentarioRepo.delete(comentario);
        }} catch (Exception e) {
          throw new RuntimeException();
       }
    }

    @Override
    public ComentarioListDto listarComentarios(Integer inmuebleId, int page, int size) {
        try {
            if (inmuebleId == null) {
                throw new IllegalArgumentException("Ingrese un id valido");
            }
            Inmueble inmueble = inmuebleRepo.findById(inmuebleId)
                    .orElseThrow(()-> new NoSuchElementException("No se ah encontrado el inmueble"));

            Pageable pageable = PageRequest.of(page, size);
            Page<Comentario> comentarioPage = comentarioRepo.findByInmueble(inmueble.getId(), pageable);

            List<String> filePath = inmueble.getImagenes().stream()
                    .map(Imagen::getFilePath).collect(Collectors.toList());
            List<String> comentarios = comentarioPage.getContent().stream()
                    .map(Comentario::getContenido).collect(Collectors.toList());

            List<Date> fecha = comentarioPage.getContent()
                    .stream().map(Comentario::getFechaCreacion).collect(Collectors.toList());
            List<String> nombreUser = comentarioPage.getContent()
                    .stream().map(comentario -> comentario.getUser().getName()).collect(Collectors.toList());


            return new ComentarioListDto(
                    inmueble.getNombre(),
                    filePath,
                    comentarios,
                    fecha,
                    nombreUser
            );
        }catch(IllegalArgumentException | NoSuchElementException e){
            e.printStackTrace();
            throw new RuntimeException("No se puede listar los comentarios");
        }


    }


}

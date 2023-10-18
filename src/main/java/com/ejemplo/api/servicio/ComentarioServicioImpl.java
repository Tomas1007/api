package com.ejemplo.api.servicio;

import com.ejemplo.api.dto.ComentarioDto;
import com.ejemplo.api.dto.ComentarioGuardarDto;

import com.ejemplo.api.dto.ComentarioListDto;
import com.ejemplo.api.dto.ComentarioUpd;
import com.ejemplo.api.entidades.Comentario;

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
    public ComentarioDto crearComentario(ComentarioGuardarDto comentarioGuardarDto, String email, Integer inmuebleId) {
       try {

           if (email.isEmpty()|| inmuebleId == null) {
               throw new IllegalArgumentException("Usuario e inmueble invalidos");
           }
           User user = userRepository.findByEmail(email)
                   .orElseThrow(() -> new NoSuchElementException("No se ha encontrado un usuario valido"));
           Inmueble inmueble = inmuebleRepo.findById(inmuebleId)
                   .orElseThrow(() -> new NoSuchElementException("No se ha encontrado un inmueble valido"));

           Comentario comentario = new Comentario();
           comentario.setUser(user);
           comentario.setInmueble(inmueble);
           comentario.setContenido(comentarioGuardarDto.contenido());
           comentario.setFechaCreacion(comentarioGuardarDto.fechaCreacion());

           ComentarioDto comentarioDto = new ComentarioDto(
                   inmueble.getTitulo(),
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
    public ComentarioDto actualizarComentario(String email, Integer inmuebleId, ComentarioUpd comentarioUpd) {
       try {

           if (email.isEmpty() || inmuebleId == null) {
               throw new IllegalArgumentException("Ingrese valores reales");
           }
           User user = userRepository.findByEmail(email)
                   .orElseThrow(() -> new NoSuchElementException("No se ha encontrado un usuario valido"));
           Inmueble inmueble = inmuebleRepo.findById(inmuebleId)
                   .orElseThrow(() -> new NoSuchElementException("No se ha encontrado un inmueble valido"));

           Comentario comentarioExistente = comentarioRepo.findById(comentarioUpd.id())
                   .orElseThrow(() -> new NoSuchElementException("No se ah encontrado un comentario valido"));
           comentarioExistente.setContenido(comentarioUpd.contenido());

           ComentarioDto comentarioDto = new ComentarioDto(
                   inmueble.getTitulo(),
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

   public Page<ComentarioListDto> listarComentariosInmueble(Integer inmuebleId, int page, int size) {
       if (inmuebleId == null) {
           throw new IllegalArgumentException("Ingrese un ID de inmueble válido");
       }
       Inmueble inmueble = inmuebleRepo.findById(inmuebleId)
               .orElseThrow(() -> new NoSuchElementException("No se encontró el inmueble con el ID proporcionado"));
       Pageable pageable = PageRequest.of(page, size);
       Page<Comentario> comentariosPage = comentarioRepo.findByInmueble(inmueble, pageable);

       Page<ComentarioListDto> comentarios = comentariosPage
                .map(c -> new ComentarioListDto(
                        c.getContenido(),
                        c.getFechaCreacion(),
                        c.getUser().getName()
                ));
        return comentarios;

   }


    }



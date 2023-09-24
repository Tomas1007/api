package com.ejemplo.api.entidades;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name= "inmuebles")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Inmueble implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String descripcion;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fechaCreacion;
    private boolean pileta;
    private boolean parrilla;

    @OneToMany(mappedBy = "inmueble", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Imagen> imagenes = new HashSet<>();

    @OneToMany(mappedBy = "inmueble", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Comentario> comentarios = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
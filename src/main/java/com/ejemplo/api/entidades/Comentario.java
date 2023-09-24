package com.ejemplo.api.entidades;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name= "comentarios")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Comentario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "inmueble_id", referencedColumnName = "id")
    private Inmueble inmueble;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private String contenido;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fechaCreacion;
}

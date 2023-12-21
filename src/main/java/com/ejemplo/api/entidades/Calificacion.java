package com.ejemplo.api.entidades;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name= "calificaciones")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer calificacion;

    @ManyToOne
    @JoinColumn(name = "inmueble_id", referencedColumnName = "id")
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Inmueble inmueble;
}

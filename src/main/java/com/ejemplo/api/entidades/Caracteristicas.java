package com.ejemplo.api.entidades;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name= "caracteristicas")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Caracteristicas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private boolean piscina;
    private boolean wifi;
    private boolean parrilla;
    private Integer cantidadPersonas;
    private Integer habitaciones;
    private Integer banios;

    @OneToOne
    @JoinColumn(name = "inmueble_id", referencedColumnName = "id")
    @JsonBackReference
    private Inmueble inmueble;
}

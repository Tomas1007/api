package com.ejemplo.api.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name= "imagenes")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String tipo;
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "inmueble_id", referencedColumnName = "id")
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)

    @JsonBackReference
    private Inmueble inmueble;




}

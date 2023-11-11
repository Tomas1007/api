package com.ejemplo.api.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name= "imagen_portada")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ImagenPortada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String tipo;
    private String filePath;

    @OneToOne
    @JoinColumn(name = "inmueble_id", referencedColumnName = "id")
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)

    @JsonBackReference
    private Inmueble inmueble;
}

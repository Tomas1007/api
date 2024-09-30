package com.ejemplo.api.entidades;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "reservas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Reservas implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reservaId;

    @ManyToOne
    @JoinColumn(name = "inmueble_id", referencedColumnName = "id")
    private Inmueble inmueble;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date fechaReserva;

    private int estado;

}

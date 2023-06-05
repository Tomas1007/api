package com.ejemplo.api.dto;

import com.ejemplo.api.entidades.Imagen;
import com.ejemplo.api.entidades.Inmueble;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class InmueblesImagenesDTO {

    private Inmueble inmueble;
    private List<String> pathImagenes;

}

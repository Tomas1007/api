package com.ejemplo.api.servicio;

import com.ejemplo.api.entidades.Caracteristicas;
import com.ejemplo.api.entidades.Inmueble;
import com.ejemplo.api.repository.CaracteristicasRepo;
import com.ejemplo.api.repository.InmuebleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CaracteristicasServicioImpl implements CaractetisticasServicio{

    private final CaracteristicasRepo caracteristicasRepo;
    private final InmuebleRepo inmuebleRepo;

    @Override
    public Caracteristicas guardar(Caracteristicas caracteristicasGuardar, Integer idInmueble) {
        try {
            if (idInmueble == null) {
                throw new IllegalArgumentException("Ingrese un inmueble valido");
            }
            Inmueble inmueble = inmuebleRepo.findById(idInmueble)
                    .orElseThrow(() -> new NoSuchElementException("No se ha encontrado un inmueble valido"));

            Caracteristicas caracteristicas = new Caracteristicas();
            caracteristicas.setPiscina(caracteristicasGuardar.isPiscina());
            caracteristicas.setWifi(caracteristicasGuardar.isWifi());
            caracteristicas.setParrilla(caracteristicasGuardar.isParrilla());
            caracteristicas.setTv(caracteristicasGuardar.isTv());
            caracteristicas.setCantidadPersonas(caracteristicasGuardar.getCantidadPersonas());
            caracteristicas.setHabitaciones(caracteristicasGuardar.getHabitaciones());
            caracteristicas.setBanios(caracteristicasGuardar.getBanios());
            caracteristicas.setInmueble(inmueble);

            return caracteristicasRepo.save(caracteristicas);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo guardar las caracteristicas");
        }
    }
    @Override
    public List<Caracteristicas> listarCaracteristicasPorInmueble(Integer id) {
        try {
            if(id == null){
                throw new IllegalArgumentException("Ingrese un id valido");
            }
            Inmueble inmueble = inmuebleRepo.findById(id)
                    .orElseThrow(()-> new NoSuchElementException("No se encontro un inmueble con ese Id"));

            return caracteristicasRepo.findByInmueble(inmueble);
        }catch (Exception e){
            throw new RuntimeException("No se pudo listar las caracteristicas");
        }
        }


    @Override
    public void eliminar(Integer id) {
        try{
         if(id == null){
             throw new IllegalArgumentException("Ingrese un id valido");
         }
         Caracteristicas caracteristicas = caracteristicasRepo.findById(id)
                 .orElseThrow(()-> new NoSuchElementException("No se encontro las caractertisticas con Id"));
         caracteristicasRepo.delete(caracteristicas);
        }catch (Exception e){
            throw new RuntimeException("No se pudo eliminar las caracteristicas");
        }
    }
}

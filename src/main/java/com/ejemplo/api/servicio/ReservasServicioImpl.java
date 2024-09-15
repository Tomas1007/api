package com.ejemplo.api.servicio;

import com.ejemplo.api.dto.ReservaDto;
import com.ejemplo.api.entidades.Inmueble;
import com.ejemplo.api.entidades.Reservas;
import com.ejemplo.api.entidades.User;
import com.ejemplo.api.repository.InmuebleRepo;
import com.ejemplo.api.repository.ReservaRepo;
import com.ejemplo.api.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;




import java.util.Calendar;

import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class ReservasServicioImpl implements ReservasServicio {

    private final ReservaRepo reservaRepo;
    private final InmuebleRepo inmuebleRepo;

    private final UserRepository userRepository;

    @Override
    public ReservaDto guardarReserva(Reservas reservas, String email, Integer idInmueble) {

        Calendar c = Calendar.getInstance();
        try {
            if (email.isEmpty() || idInmueble == null) {
                throw new IllegalArgumentException("Usuario e inmueble invalidos");
            }
            if (reservas.getFechaReserva().before(c.getTime())) {
                throw new IllegalArgumentException("Ingrese una fecha correcta");
            }
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new NoSuchElementException("No se ha encontrado un usuario valido"));
            Inmueble inmueble = inmuebleRepo.findById(idInmueble)
                    .orElseThrow(() -> new NoSuchElementException("No se ha encontrado un inmueble valido"));


            Reservas reservas1 = new Reservas();
            reservas1.setInmueble(inmueble);
            reservas1.setFechaReserva(reservas.getFechaReserva());
            reservas1.setEstado(reservas.getEstado());
            reservas1.setUserId(user);

            reservaRepo.save(reservas1);

            return new ReservaDto(
                    inmueble.getTitulo(),
                    inmueble.getPrecio(),
                    reservas1.getFechaReserva(),
                    user.getName(),
                    user.getEmail()
            );
        } catch (NoSuchElementException e) {
            throw new RuntimeException(e);
        }
    }

    //@Override
    /*public Reservas reservaByUserEmail(String email) {

        try {
        if(email.isEmpty()) {
            throw new IllegalArgumentException("Ingrese un email valido");
        }

        Reservas reservas = reservaRepo.getReservasByEmail(email);
        reservas.



        } catch () {


    }*/
}
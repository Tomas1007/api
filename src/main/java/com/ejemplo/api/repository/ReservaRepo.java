package com.ejemplo.api.repository;

import com.ejemplo.api.entidades.Reservas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepo extends JpaRepository<Reservas, Integer> {

    // @Query("SELECT *, users.nombre FROM `reservas` JOIN users ON user_id = users.id WHERE users.email = \"mauro@gmail.com\";")
    // Reservas getReservasByEmail(String email);
}

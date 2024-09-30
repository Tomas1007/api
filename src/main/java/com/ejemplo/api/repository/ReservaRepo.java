package com.ejemplo.api.repository;

import com.ejemplo.api.entidades.Reservas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepo extends JpaRepository<Reservas, Integer> {

     List<Reservas> findByUserEmail(String email);
}

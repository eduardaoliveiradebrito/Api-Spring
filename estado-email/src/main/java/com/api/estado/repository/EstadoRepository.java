package com.api.estado.repository;

import java.util.UUID;

//possuir metodos prontos para bd como, "select"
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.estado.models.EstadoModel;

@Repository
public interface EstadoRepository extends JpaRepository<EstadoModel, Integer>{

	boolean existsByNome(String nome);
}
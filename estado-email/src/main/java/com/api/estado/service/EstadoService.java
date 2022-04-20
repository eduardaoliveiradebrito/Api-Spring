package com.api.estado.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.api.estado.models.EstadoModel;
import com.api.estado.repository.EstadoRepository;

/**
 * 
 * @author duda
 * Essa classe/ service vai ser um bin
 * E uma camada intermediaria entre o controller e o repository, ou seja o
 * controller acionar o service e o service acionar o repositore
 *
 */

@Service
public class EstadoService {
	
	final EstadoRepository estadoRepository;

	public EstadoService(EstadoRepository estadoRepository) {
		this.estadoRepository = estadoRepository;
	}

	@Transactional 
	public EstadoModel save(EstadoModel estadoModel) {
		return estadoRepository.save(estadoModel);
	}

	public boolean existsByNome(String nome) {
		return estadoRepository.existsByNome(nome);
	}

	public List<EstadoModel> findAll() {
		return estadoRepository.findAll();
	}

	public Optional<EstadoModel> findById(int id) {
		return estadoRepository.findById(id);
	}

	@Transactional
	public void delete(EstadoModel estadoModel) {
		estadoRepository.delete(estadoModel);
	}


}

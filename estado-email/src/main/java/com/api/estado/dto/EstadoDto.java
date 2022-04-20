package com.api.estado.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * criacao dos dados de entrada usando nossa entidade do pacote Model
 * validacao dos dados
 */

public class EstadoDto {

	//valicadao dos dados feita via spring validation
	private boolean ativo;

	@NotBlank
	@Size(max = 2)
	private String sigla;

	@NotBlank
	@Size(max = 50)
	private String nome;

	//gets e sets
	public boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
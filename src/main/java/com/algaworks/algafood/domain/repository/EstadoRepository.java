package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.Estado;

public interface EstadoRepository {

	Estado salvar(Estado estado);

	List<Estado> listar();
	
	Estado buscar(Long id);

	void remover(Long id);
}


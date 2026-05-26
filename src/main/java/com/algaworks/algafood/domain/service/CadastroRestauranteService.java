package com.algaworks.algafood.domain.service;

import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	private RestauranteRepository restauranteRepository;
	
	public Restaurante buscar (Long restauranteId) {
		
		return restauranteRepository.buscar(restauranteId);
	}
	
}

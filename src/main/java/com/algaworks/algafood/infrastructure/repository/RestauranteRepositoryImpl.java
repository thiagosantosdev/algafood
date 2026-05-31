package com.algaworks.algafood.infrastructure.repository;

import org.springframework.stereotype.Repository;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepository{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Restaurante buscar(Long id) {

		return manager.find(Restaurante.class, id);
	}

	@Transactional
    @Override
    public Restaurante salvar(Restaurante restaurante) {
    	return manager.merge(restaurante);
    }
}


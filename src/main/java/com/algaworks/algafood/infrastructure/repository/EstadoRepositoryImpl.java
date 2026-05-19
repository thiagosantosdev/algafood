package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class EstadoRepositoryImpl implements EstadoRepository {

	@PersistenceContext
	private EntityManager manager;

	    @Transactional
	    @Override
	    public Estado salvar(Estado estado) {
	    	return manager.merge(estado);
	    }

		@Override
		public List<Estado> listar() {

			return manager.createQuery("from Estado", Estado.class).getResultList();
		}
}

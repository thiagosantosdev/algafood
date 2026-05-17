package com.algaworks.algafood.infrastructure.repository;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissoesRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class PermissoesRepositoryimpl implements PermissoesRepository{

	@PersistenceContext
	private EntityManager manager;
	
	    @Transactional
	    @Override
	    public Permissao salvar(Permissao permissoes) {
	    	return manager.merge(permissoes);
	    }
	
}

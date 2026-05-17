package com.algaworks.algafood.infrastructure.repository;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class CidadeRepositoryImpl implements CidadeRepository{

	@PersistenceContext
	private EntityManager manager;

	    @Transactional
	    @Override
	    public Cidade salvar(Cidade cidade) {
	    	return manager.merge(cidade);
	    }
}

package com.algaworks.algafood.infrastructure.repository;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class FormaPagamentoRepositoryImpl implements FormaPagamentoRepository {

	@PersistenceContext
	private EntityManager manager;

	    @Transactional
	    @Override
	    public FormaPagamento salvar(FormaPagamento formaPagamento) {
	    	return manager.merge(formaPagamento);
	    }
}

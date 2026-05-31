package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
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
	    

		@Override
		public List<Cidade> listar(){
			return manager.createQuery("from Cidade", Cidade.class).getResultList();
		}
		
		@Override
		public Cidade buscar(Long id) {
			return manager.find(Cidade.class, id);
		}
		@Transactional
		@Override
		public void remover(Long id) {
			Cidade cidade = buscar(id);
			
			if(cidade == null) {
				throw new EmptyResultDataAccessException(1);
			}
			manager.remove(cidade);
		}
}

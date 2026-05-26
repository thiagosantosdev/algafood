package com.algaworks.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@RestController
public class RestauranteController {
	
	//@Autowired
	//private CadastroRestauranteService cadastroRestauranteService;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@GetMapping("/restaurante/{id}")
	public ResponseEntity<Restaurante> buscar(@PathVariable("id") Long id){
		
		Restaurante restaurante = restauranteRepository.buscar(id);
		
		return new ResponseEntity<Restaurante>(restaurante, HttpStatus.OK);
	}

}
/*
@GetMapping(value = "/buscarProduto/{id_produto}")
	public ResponseEntity<Produto> buscarProduto(@PathVariable("id_produto") Long id_produto)
			throws ExceptionMentoriaJava {

		Produto produto = produtoRepository.findById(id_produto).orElse(null);

		if (produto == null) {
			throw new ExceptionMentoriaJava("Não encontrou produto com código: " + id_produto);
		}

		return new ResponseEntity<Produto>(produto, HttpStatus.OK);
	}
*/
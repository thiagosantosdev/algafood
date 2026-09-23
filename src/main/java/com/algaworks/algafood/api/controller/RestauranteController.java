package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ReflectionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;



@RequestMapping(value = "/restaurantes")
@RestController
public class RestauranteController {
	
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	
	@GetMapping(value = "/")
	public List<Restaurante> listar() {
		return restauranteRepository.findAll();
	}
	/*@GetMapping(value = "/listatodos")
	@ResponseBody
	public ResponseEntity<List<Usuario>> listaUsuario(){
		
		List<Usuario> usuarios = usuarioRepository.findAll();
		
		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
	}*/
	
	@GetMapping("/restaurantes/por-nome")
	public List<Restaurante> restaurantePorNomes(
		String nome, Long cozinhaId){
			return restauranteRepository.consultaPorNome(nome, cozinhaId);
	}
	
	@GetMapping("/restaurante/{id}")
	public ResponseEntity<Restaurante> buscar(@PathVariable("id") Long id){
		
		Optional<Restaurante> restaurante = restauranteRepository.findById(id);
		
		 	if(restaurante.isPresent()) {
		 		return ResponseEntity.ok(restaurante.get());
		 	}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	/*
	 * public ResponseEntity<?> salvar(@RequestBody Cidade cidade) {
		try {
			cidade = cadastroCidadeService.salvar(cidade);
			return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}*/
	
	
	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping
	public ResponseEntity<?> adicionar( @RequestBody @Valid Restaurante restaurante) {
		try {
			restaurante = cadastroRestauranteService.salvar(restaurante);
			
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(restaurante);
		}catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		//return cadastroRestauranteService.salvar(restaurante);
	}
	
	@PutMapping("/{restauranteId}")
	public ResponseEntity<?> atualizar(@PathVariable Long restauranteId,
			@RequestBody Restaurante restaurante) {
		try {
			Restaurante restauranteAtual = restauranteRepository
					.findById(restauranteId).orElse(null);
			
			if (restauranteAtual != null) {
				BeanUtils.copyProperties(restaurante, restauranteAtual, 
						"id", "formasPagamento", "endereco", "dataCadastro");
				
				restauranteAtual = cadastroRestauranteService.salvar(restauranteAtual);
				return ResponseEntity.ok(restauranteAtual);
			}
			
			return ResponseEntity.notFound().build();
		
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest()
					.body(e.getMessage());
		}
	}
	
	@PatchMapping("/{restauranteId}")
	public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId,
			@RequestBody Map<String, Object> campos) {
		Restaurante restauranteAtual = restauranteRepository
				.findById(restauranteId).orElse(null);
		
		if (restauranteAtual == null) {
			return ResponseEntity.notFound().build();
		}
		
		merge(campos, restauranteAtual);
		
		return atualizar(restauranteId, restauranteAtual);
	}

	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
		
		dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
			field.setAccessible(true);
			
			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
			
//			System.out.println(nomePropriedade + " = " + valorPropriedade + " = " + novoValor);
			
			ReflectionUtils.setField(field, restauranteDestino, novoValor);
		});
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}

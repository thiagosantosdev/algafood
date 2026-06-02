package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
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
import tools.jackson.databind.ObjectMapper;

@RequestMapping("/restaurantes")
@RestController
public class RestauranteController {
	
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
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
	
	
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
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
	public ResponseEntity<?> atualizar(@PathVariable Long restauranteId, @RequestBody Optional<Restaurante> restauranteAtual2){
		try {
		Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);
		if(restauranteAtual.isPresent()) {
			//cozinhaAtual.setNome(cozinha.getNome());
			BeanUtils.copyProperties(restauranteAtual2, restauranteAtual.get(), "id");
			
		    Restaurante restauranteSalvo = cadastroRestauranteService.salvar(restauranteAtual.get());
			
			
			return ResponseEntity.ok(restauranteSalvo);
			
		}
		
		return ResponseEntity.notFound().build();
	}catch(EntidadeNaoEncontradaException e) {
		
		return ResponseEntity.badRequest()
				.body(e.getMessage());
	}
	
	}
	
	@PatchMapping("/{restauranteId}")
	public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos){
		Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);
		
		if(restauranteAtual.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		merge(campos, restauranteAtual);
		
		return atualizar(restauranteId, restauranteAtual);
		
	}
	private void merge(Map<String, Object> dadosOrigem, Optional<Restaurante> restauranteAtual) {
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
	    System.out.println(restauranteOrigem);
		
		
		dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
			field.setAccessible(true);
			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
			System.out.println(nomePropriedade + " = " + valorPropriedade + " = " + novoValor);
			
			ReflectionUtils.setField(field, restauranteAtual, novoValor);
		});
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}

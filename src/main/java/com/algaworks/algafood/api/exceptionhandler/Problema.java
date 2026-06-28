package com.algaworks.algafood.api.exceptionhandler;

import java.time.LocalDateTime;

public class Problema {

	private final LocalDateTime dataHora;
	private final String mensagem;
	
	// Construtor privado para garantir que a criação passe apenas pelo Builder
	private Problema(LocalDateTime dataHora, String mensagem) {
		this.dataHora = dataHora;
		this.mensagem = mensagem;
	}

	// Método estático de entrada que substitui o "Problema.builder()"
	public static ProblemaBuilder builder() {
		return new ProblemaBuilder();
	}

	// Classe Builder estática interna
	public static class ProblemaBuilder {
		private LocalDateTime dataHora;
		private String mensagem;

		public ProblemaBuilder dataHora(LocalDateTime dataHora) {
			this.dataHora = dataHora;
			return this;
		}

		public ProblemaBuilder mensagem(String mensagem) {
			this.mensagem = mensagem;
			return this;
		}

		// Método que consolida e retorna a instância de Problema
		public Problema build() {
			return new Problema(this.dataHora, this.mensagem);
		}
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public String getMensagem() {
		return mensagem;
	}
	
}

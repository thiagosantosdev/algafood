package com.algaworks.algafood.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class Endereco {
	
	@Column(name = "endereco_cep")
	private String cep;
	@Column(name = "endereco_logradouro")
	private String logradouro;
	@Column(name = "endereco_numero")
	private String numero;
	@Column(name = "endereco_complemento")
	private String complemento;
	@Column(name = "endereco_bairro")
	private String bairro;
	
	@ManyToOne
	@JoinColumn(name = "endereco_cidade_id")
	private Cidade cidade;

	public String getCep() {
		return cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}

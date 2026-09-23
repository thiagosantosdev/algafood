package com.algaworks.algafood.api.exceptionhandler;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Problem {

	private Integer status;
	private String type;
	private String title;
	private String detail;
	
	private String userMessage;

	private List<Field> fields;
    
	// 1. Construtor privado para forçar o uso do Builder
    private Problem(Builder builder) {
        this.status = builder.status;
        this.type = builder.type;
        this.title = builder.title;
        this.detail = builder.detail;
        this.userMessage = builder.userMessage;
        this.fields = builder.fields;
    }

    // 2. Getters (Necessários para ler os dados da classe)
    public Integer getStatus() { return status; }
    public String getType() { return type; }
    public String getTitle() { return title; }
    public String getDetail() { return detail; }
    public String getUserMessage() { return userMessage; }
    public List<Field> getFields() { return fields; } 

    // 3. Método estático para iniciar a criação do Builder
    public static Builder builder() {
        return new Builder();
    }

    // 4. Classe estática interna (O Builder propriamente dito)
    public static class Builder {
        private Integer status;
        private String type;
        private String title;
        private String detail;
        private String userMessage;
        private List<Field> fields; 
        
        public Builder status(Integer status) {
            this.status = status;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder detail(String detail) {
            this.detail = detail;
            return this;
        }
        
        public Builder userMessage(String userMessage) {
            this.userMessage = userMessage;
            return this;
        }
        public Builder fields(List<Field> fields) {
            this.fields = fields;
            return this;
        }

        // Método que valida (opcional) e entrega a instância final de Problem
        public Problem build() {
            return new Problem(this);
        }
    }

	
 // Classe auxiliar para representar cada campo com erro no JSON
    public static class Field {
        private String name;
        private String userMessage;

        public Field(String name, String userMessage) {
            this.name = name;
            this.userMessage = userMessage;
        }

        public String getName() { return name; }
        public String getUserMessage() { return userMessage; }
    }
	
	
}

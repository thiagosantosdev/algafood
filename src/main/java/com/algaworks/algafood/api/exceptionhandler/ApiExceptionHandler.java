package com.algaworks.algafood.api.exceptionhandler;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders; //  ADICIONE ESTE IMPORT DO SPRING
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algafood.api.exceptionhandler.Problem.Builder;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;		
		ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;
		String detail = "Ocorreu um erro interno inesperado no sistema. "
				+ "Tente novamente e se o problema persistir, entre em contato "
				+ "com o administrador do sistema.";

		// Importante colocar o printStackTrace (pelo menos por enquanto, que não estamos
		// fazendo logging) para mostrar a stacktrace no console
		// Se não fizer isso, você não vai ver a stacktrace de exceptions que seriam importantes
		// para você durante, especialmente na fase de desenvolvimento
		ex.printStackTrace();
		
		Problem problem = createProblemBuilder(status, problemType, detail).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

	    ProblemType problemType = ProblemType.DADOS_INVALIDOS;
	    String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
	    
	    BindingResult bindingResult = ex.getBindingResult();
	    
	    List<Problem.Field> problemFields = bindingResult.getFieldErrors().stream()
	            .map(fieldError -> new Problem.Field(
	                    fieldError.getField(),
	                    fieldError.getDefaultMessage()
	            ))
	            .collect(Collectors.toList());

	    
	    Problem problem = createProblemBuilder(status, problemType, detail)
	        .userMessage(detail)
	        .fields(problemFields)
	        .build();
	    
	    return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, 
	        HttpHeaders headers, HttpStatusCode status, WebRequest request) { // 🌟 Alterado para HttpStatusCode!
	    
	    ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
	    String detail = String.format("O recurso '%s', que você tentou acessar, é inexistente.", 
	            ex.getRequestURL());
	    
	    // Fazemos o cast para HttpStatus para que o seu createProblemBuilder aceite sem reclamar
	    Problem problem = createProblemBuilder((HttpStatus) status, problemType, detail).build();
	    
	    return handleExceptionInternal(ex, problem, headers, status, request);
	}

@ExceptionHandler(MethodArgumentTypeMismatchException.class)
public ResponseEntity<Object> handleMethodArgumentTypeMismatch(    
        MethodArgumentTypeMismatchException ex, WebRequest request) {
	                          
    // Como não estamos herdando da assinatura do pai, criamos os headers e o status na mão de forma limpa
    HttpHeaders headers = new HttpHeaders();
    HttpStatus status = HttpStatus.BAD_REQUEST; 

    ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;

    String targetType = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "esperado";

    String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
            + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
            ex.getName(), ex.getValue(), targetType);

    Problem problem = createProblemBuilder(status, problemType, detail).build();

    return handleExceptionInternal(ex, problem, headers, status, request);
}



	

	
@ExceptionHandler(PropertyBindingException.class) // 🌟 O Spring agora vai chamar este método sozinho!
public ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, WebRequest request) {
    
    // Criamos os headers e o status corretos aqui dentro, já que o Spring Boot 2 usa HttpStatus
    HttpHeaders headers = new HttpHeaders();
    HttpStatus status = HttpStatus.BAD_REQUEST; 
    
    // Seu código original que junta os caminhos das propriedades
    String path = joinPath(ex.getPath());
    
    ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
    String detail = String.format("A propriedade '%s' não existe. "
            + "Corrija ou remova essa propriedade e tente novamente.", path);

    Problem problem = createProblemBuilder(status, problemType, detail).build();
    
    return handleExceptionInternal(ex, problem, headers, status, request);
}
	
	//@Override
	protected  ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
					HttpHeaders headers, HttpStatus status, WebRequest request) {
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		
		if (rootCause instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
		}
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";
		
		Problem problem = createProblemBuilder(status, problemType, detail).build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String path = ex.getPath().stream()
				.map(ref -> ref.getFieldName())
				.collect(Collectors.joining("."));
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = String.format("A propriedade '%s' recebeu o valor '%s', "
				+ "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
				path, ex.getValue(), ex.getTargetType().getSimpleName());
		
		Problem problem = createProblemBuilder(status, problemType, detail).build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontradaException(
			EntidadeNaoEncontradaException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		String detail = ex.getMessage();
		
		Problem problem = createProblemBuilder(status, problemType, detail).build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUsoException(
			EntidadeEmUsoException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.CONFLICT;
		ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
		String detail = ex.getMessage();
		
		Problem problem = createProblemBuilder(status, problemType, detail).build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest request) {

		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.ERRO_NEGOCIO;
		String detail = ex.getMessage();
		
		Problem problem = createProblemBuilder(status, problemType, detail).build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if (body == null) {
			body = Problem.builder()
				.title(status.getReasonPhrase())
				.status(status.value())
				.build();
		} else if (body instanceof String) {
			body = Problem.builder()
				.title((String) body)
				.status(status.value())
				.build();
		}
		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	private Builder createProblemBuilder(HttpStatusCode status, ProblemType problemType, String detail) {
		
	    return Problem.builder()
	        .status(status.value())
	        .type(problemType.getUri())
	        .title(problemType.getTitle())
	        .detail(detail);
	}

	
	
	
	
	private String joinPath(List<Reference> references) {
		return references.stream()
			.map(ref -> ref.getFieldName())
			.collect(Collectors.joining("."));
	}
	
	
	
	
	
	
	
}

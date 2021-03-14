package br.com.stoom.ms.adress.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.stoom.ms.adress.exception.TechnicalException;
import br.com.stoom.ms.adress.model.Response;

@RestControllerAdvice
public class ExceptionHandlingController {
	@ExceptionHandler({Exception.class, IllegalArgumentException.class, TechnicalException.class})
	  public ResponseEntity<Response> databaseError(Exception e) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new Response(e.getMessage()));
	  }
}

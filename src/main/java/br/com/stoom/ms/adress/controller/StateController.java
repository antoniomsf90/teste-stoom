package br.com.stoom.ms.adress.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.stoom.ms.adress.exception.TechnicalException;
import br.com.stoom.ms.adress.model.State;
import br.com.stoom.ms.adress.service.StateService;

@RestController
@RequestMapping("/state")
public class StateController {
	@Autowired
	private StateService service;
	
	@GetMapping
	public @ResponseBody List<State> findAll() throws TechnicalException {
		return service.findAll();
	}
	
	@GetMapping("{id}")
	public @ResponseBody State findAll(@PathVariable("id") String id) throws TechnicalException {
		return service.findById(id);
	}
	
	@PostMapping
	public @ResponseBody State newState(@RequestBody State state) throws TechnicalException {
		return service.save(state);
	}
	
	@PutMapping
	public @ResponseBody State updateState(@RequestBody State state) throws TechnicalException {
		return service.update(state);
	}
	
	@DeleteMapping
	public void deleteState(@RequestBody State state) throws TechnicalException {
		service.delete(state);
	}
}

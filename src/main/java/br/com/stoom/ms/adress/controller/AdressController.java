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
import br.com.stoom.ms.adress.model.Adress;
import br.com.stoom.ms.adress.service.AdressService;

@RestController
@RequestMapping("/adress")
public class AdressController {
	@Autowired
	private AdressService service;
	
	@GetMapping
	public @ResponseBody List<Adress> findAll() throws TechnicalException {
		return service.findAll();
	}
	
	@GetMapping("{id}")
	public @ResponseBody Adress findAll(@PathVariable("id") String id) throws TechnicalException {
		return service.findById(id);
	}
	
	@PostMapping
	public @ResponseBody Adress newAdress(@RequestBody Adress adress) throws TechnicalException {
		return service.save(adress);
	}
	
	@PutMapping
	public @ResponseBody Adress updateAdress(@RequestBody Adress adress) throws TechnicalException {
		return service.update(adress);
	}
	
	@DeleteMapping
	public void deleteAdress(@RequestBody Adress adress) throws TechnicalException {
		service.delete(adress);
	}
	
	
}

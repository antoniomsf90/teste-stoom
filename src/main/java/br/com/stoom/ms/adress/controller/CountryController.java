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
import br.com.stoom.ms.adress.model.Country;
import br.com.stoom.ms.adress.service.CountryService;

@RestController
@RequestMapping("/country")
public class CountryController {
	@Autowired
	private CountryService service;
	
	@GetMapping
	public @ResponseBody List<Country> findAll() throws TechnicalException {
		return service.findAll();
	}
	
	@GetMapping("{id}")
	public @ResponseBody Country findAll(@PathVariable("id") String id) throws TechnicalException {
		return service.findById(id);
	}
	
	@PostMapping
	public @ResponseBody Country newCountry(@RequestBody Country country) throws TechnicalException {
		return service.save(country);
	}
	
	@PutMapping
	public @ResponseBody Country updateCountry(@RequestBody Country country) throws TechnicalException {
		return service.update(country);
	}
	
	@DeleteMapping
	public void deleteCountry(@RequestBody Country country) throws TechnicalException {
		service.delete(country);
	}
}
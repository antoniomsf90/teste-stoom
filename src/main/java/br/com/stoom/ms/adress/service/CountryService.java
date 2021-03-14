package br.com.stoom.ms.adress.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.com.stoom.ms.adress.exception.TechnicalException;
import br.com.stoom.ms.adress.model.Country;
import br.com.stoom.ms.adress.repository.CountryRepository;

@Service
public class CountryService {
	@Autowired
	private CountryRepository repository;
	
	public List<Country> findAll() {
		return repository.findAll();
	}
	
	public Country findById(String id) throws TechnicalException {
		Optional<Country> optCountry = repository.findById(id);
		if(optCountry.isPresent()) {
			return optCountry.get();
		} else {
			throw new TechnicalException("Error while trying to find country: Country not found with id "+id);
		}
	}
	
	public Country save(Country country) throws TechnicalException {
		try {
			Assert.isNull(country.getId(), "I can not save an existing country");
			this.validateCountry(country);
			return repository.save(country);
		} catch(IllegalArgumentException e) {
			throw new TechnicalException("Error while trying to save country: "+e.getMessage());
		}
	}
	
	public Country update(Country country) throws TechnicalException {
		try {
			Assert.notNull(country, "Country can not be null");
			Assert.notNull(country.getId(), "I can not update an country withoud and ID");
			Assert.notNull(this.findById(country.getId()), "I can not update an non existing country in database");
			this.validateCountry(country);
			return repository.save(country);
		} catch(IllegalArgumentException e) {
			throw new TechnicalException("Error while trying to update country: "+e.getMessage());
		}
	}
	
	public void delete(Country country) throws TechnicalException {
		try {
			Assert.notNull(country, "full country can not be null");
			Assert.notNull(country.getId(), "I can not delete an country withoud and ID");
			Assert.notNull(this.findById(country.getId()), "I can not delete an non existing country in database");
			repository.delete(country);
		} catch(IllegalArgumentException e) {
			throw new TechnicalException("Error while trying to update country: "+e.getMessage());
		}
	}
	
	private void validateCountry(Country country) {
		Assert.notNull(country.getName(), "Country name can not be null");
		Assert.isTrue(!"".equals(country.getName()), "Country name can not be empty");
	}
}

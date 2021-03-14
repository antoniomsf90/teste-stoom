package br.com.stoom.ms.adress.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.com.stoom.ms.adress.exception.TechnicalException;
import br.com.stoom.ms.adress.model.State;
import br.com.stoom.ms.adress.repository.StateRepository;

@Service
public class StateService {
	
	@Autowired
	private StateRepository repository;
	
	@Autowired
	private CountryService countryService;
	
	public List<State> findAll() {
		return repository.findAll();
	}
	
	public State findById(String id) throws TechnicalException {
		Optional<State> optState = repository.findById(id);
		if(optState.isPresent()) {
			return optState.get();
		} else {
			throw new TechnicalException("State not found with id "+id);
		}
	}
	
	public State save(State state) throws TechnicalException {
		try {
			Assert.isNull(state.getId(), "I can not save an existing state");
			this.validateState(state);
			return repository.save(state);
		} catch(IllegalArgumentException e) {
			throw new TechnicalException("Error while trying to save state: "+e.getMessage());
		}
	}
	
	public State update(State state) throws TechnicalException {
		try {
			Assert.notNull(state, "State can not be null");
			Assert.notNull(state.getId(), "I can not update an state withoud and ID");
			Assert.notNull(this.findById(state.getId()), "I can not update an non existing state in database");
			this.validateState(state);
			return repository.save(state);
		} catch(IllegalArgumentException e) {
			throw new TechnicalException("Error while trying to update state: "+e.getMessage());
		}
	}
	
	public void delete(State state) throws TechnicalException {
		try {
			Assert.notNull(state, "full state can not be null");
			Assert.notNull(state.getId(), "I can not delete an state withoud and ID");
			Assert.notNull(this.findById(state.getId()), "I can not delete an non existing state in database");
			repository.delete(state);
		} catch(IllegalArgumentException e) {
			throw new TechnicalException("Error while trying to delete state : "+e.getMessage());
		}
	}
	
	private void validateState(State state) throws TechnicalException {
		Assert.notNull(state.getName(), "State name can not be null");
		Assert.isTrue(!"".equals(state.getName()), "State name can not be empty");
		
		Assert.notNull(state.getCountry(), "Country can not be null");
		Assert.notNull(state.getCountry().getId(), "Country id can not be null");
		Assert.isTrue(!"".equals(state.getCountry().getId()), "Country id can not be empty");
		Assert.notNull(countryService.findById(state.getCountry().getId()), "Country not found with id "+state.getCountry().getId());
	}
}

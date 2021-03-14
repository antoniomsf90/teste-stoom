package br.com.stoom.ms.adress.service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.com.stoom.ms.adress.exception.TechnicalException;
import br.com.stoom.ms.adress.model.Adress;
import br.com.stoom.ms.adress.model.GeocodeDto;
import br.com.stoom.ms.adress.repository.AdressRepository;

@Service
public class AdressService {
	@Autowired
	private AdressRepository repository;
	
	@Autowired
	private GoogleGeocodingService googleGeocodingService;
	
	@Autowired
	private StateService stateService;
	
	public List<Adress> findAll() {
		return repository.findAll();
	}
	
	public Adress findById(String id) throws TechnicalException {
		Optional<Adress> adress = repository.findById(id);
		if(adress.isPresent()) {
			return adress.get();
		} else {
			throw new TechnicalException("Adress not found with id "+id);
		}
	}
	
	public Adress save(Adress adress) throws TechnicalException {
		try {
			Assert.notNull(adress, "Full adress can not be null");
			Assert.isNull(adress.getId(), "I can not save an existing andress");
			this.validateAdress(adress);
			this.updateGeoCode(adress);
			return repository.save(adress);
		} catch(IllegalArgumentException e) {
			throw new TechnicalException("Error while trying to save adress: "+e.getMessage());
		}
	}
	
	public Adress update(Adress adress) throws TechnicalException {
		try {
			Assert.notNull(adress, "Adress can not be null");
			Assert.notNull(adress.getId(), "I can not update an andress withoud and ID");
			Assert.notNull(this.findById(adress.getId()), "I can not update an non existing adress in database");
			this.validateAdress(adress);
			this.updateGeoCode(adress);
			return repository.save(adress);
		} catch(IllegalArgumentException e) {
			throw new TechnicalException("Error while trying to update adress: "+e.getMessage());
		}
	}
	
	public void delete(Adress adress) throws TechnicalException {
		try {
			Assert.notNull(adress, "full Adress can not be null");
			Assert.notNull(adress.getId(), "I can not delete an andress withoud and ID");
			Assert.notNull(this.findById(adress.getId()), "I can not delete an non existing adress in database");
			repository.delete(adress);
		} catch(IllegalArgumentException e) {
			throw new TechnicalException("Error while trying to delete adress: "+e.getMessage());
		}
	}
	
	private void updateGeoCode(Adress adress) {
		if(adress.getLatitude() == null || adress.getLongitude() == null) {
			GeocodeDto geocodeDto = googleGeocodingService.getGeocode(adress);
			adress.setLatitude(geocodeDto.getLatitude());
			adress.setLongitude(geocodeDto.getLongitude());
		}
	}
	
	private void validateAdress(Adress adress) throws TechnicalException {
		Assert.notNull(adress.getStreetName(), "Street name can not be null");
		Assert.isTrue(!"".equals(adress.getStreetName()), "Street name can not be empty");
		
		Assert.notNull(adress.getNumber(), "Number can not be null");
		Assert.isTrue(!"".equals(adress.getNumber()), "Number can not be empty");
		
		Assert.notNull(adress.getNeighbourhood(), "Neighbourhood can not be null");
		Assert.isTrue(!"".equals(adress.getNeighbourhood()), "Neighbourhood can not be empty");
		
		Assert.notNull(adress.getCity(), "City name can not be null");
		Assert.isTrue(!"".equals(adress.getCity()), "City name can not be empty");
		
		Assert.notNull(adress.getState(), "State can not be null");
		Assert.notNull(adress.getState().getId(), "State id can not be null");
		Assert.isTrue(!"".equals(adress.getState().getId()), "State id can not be empty");
		Assert.notNull(stateService.findById(adress.getState().getId()), "State not found");
		
		Assert.notNull(adress.getState().getCountry(), "Country can not be null");
		Assert.notNull(adress.getState().getCountry().getId(), "Country id can not be null");
		Assert.isTrue(!"".equals(adress.getState().getCountry().getId()), "Country id can not be empty");
		
		Assert.notNull(adress.getZipcode(), "Zip Code name can not be null");
		Assert.isTrue(Pattern.compile("[0-9]{8}").matcher(adress.getZipcode()).find(), "Zip Code is not valid");		
	}
}

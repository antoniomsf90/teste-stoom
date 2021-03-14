package br.com.stoom.ms.adress;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.stoom.ms.adress.exception.TechnicalException;
import br.com.stoom.ms.adress.model.Adress;
import br.com.stoom.ms.adress.model.Country;
import br.com.stoom.ms.adress.model.GeocodeDto;
import br.com.stoom.ms.adress.model.State;
import br.com.stoom.ms.adress.repository.AdressRepository;
import br.com.stoom.ms.adress.service.AdressService;
import br.com.stoom.ms.adress.service.GoogleGeocodingService;
import br.com.stoom.ms.adress.service.StateService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdressTest {
	
	public static final String SOME_RANDOM_ID = "someRandomId";
	@InjectMocks
	private AdressService service;
	
	@Mock
	private AdressRepository repository;
	
	@Mock 
	private StateService stateService;
	
	@Mock
	private GoogleGeocodingService googleGeocodingService;
	
	@Test
	public void findAll() {
		Mockito.when(repository.findAll()).thenReturn(Lists.newArrayList());
		List<Adress> states = service.findAll();
		assertEquals(0, states.size());
	}
	
	@Test
	public void findByAll2() {
		Country country = Country.builder().name("Brasil").id(SOME_RANDOM_ID).build();
		State state = State.builder().name("SP").id(SOME_RANDOM_ID).country(country).build();
		Adress adress = Adress.builder().id(SOME_RANDOM_ID).streetName("Avenida Paulista").number("3000").neighbourhood("Bela Vista").city("São Paulo").state(state).zipcode("00000000").build();
		List<Adress> adresses = Lists.newArrayList();
		adresses.add(adress);
		Mockito.when(repository.findAll()).thenReturn(adresses);
		List<Adress> result = service.findAll();
		assertEquals(1, result.size());
	}
	
	@Test
	public void findById1() throws TechnicalException {
		Country country = Country.builder().name("Brasil").id(SOME_RANDOM_ID).build();
		State state = State.builder().name("SP").id(SOME_RANDOM_ID).country(country).build();
		Adress adress = Adress.builder().id(SOME_RANDOM_ID).streetName("Avenida Paulista").number("3000").neighbourhood("Bela Vista").city("São Paulo").state(state).zipcode("00000000").build();
		Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(adress));
		Adress stateResult = service.findById(SOME_RANDOM_ID);
		assertEquals(SOME_RANDOM_ID, stateResult.getId());
	}
	
	@Test
	public void findById2() {
		try {
			Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(null));
			service.findById(SOME_RANDOM_ID);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Adress not found with id someRandomId", e.getMessage());
		}
	}
	
	@Test
	public void update1() throws TechnicalException {
		Country country = Country.builder().name("Brasil").id(SOME_RANDOM_ID).build();
		State state = State.builder().name("SP").id(SOME_RANDOM_ID).country(country).build();
		Adress adress = Adress.builder().id(SOME_RANDOM_ID).streetName("Avenida Paulista").number("3000").neighbourhood("Bela Vista").city("São Paulo").state(state).zipcode("00000000").build();
		Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(adress));
		GeocodeDto geoCodeDto = GeocodeDto.builder().latitude(-23.5546455d).longitude(-46.66375300000001d).build();
		Mockito.when(googleGeocodingService.getGeocode(adress)).thenReturn(geoCodeDto);
		Mockito.when(stateService.findById(SOME_RANDOM_ID)).thenReturn(state);
		Mockito.when(repository.save(adress)).thenReturn(adress);
		Adress result = service.update(adress);
		assertEquals(adress, result);
	}
	
	@Test
	public void update2() throws TechnicalException {
		try {
			service.update(null);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to update adress: Adress can not be null", e.getMessage());
		}
	}
	
	@Test
	public void update3() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id(SOME_RANDOM_ID).build();
			State state = State.builder().name("SP").id(SOME_RANDOM_ID).country(country).build();
			Adress adress = Adress.builder().streetName("Avenida Paulista").number("3000").neighbourhood("Bela Vista").city("São Paulo").state(state).zipcode("00000000").build();
			Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(adress));
			service.update(adress);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to update adress: I can not update an andress withoud and ID", e.getMessage());
		}
	}
	
	@Test
	public void update4() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id(SOME_RANDOM_ID).build();
			State state = State.builder().name("SP").id(SOME_RANDOM_ID).country(country).build();
			Adress adress = Adress.builder().id(SOME_RANDOM_ID).streetName("Avenida Paulista").number("3000").neighbourhood("Bela Vista").city("São Paulo").state(state).zipcode("00000000").build();
			Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(null));
			service.update(adress);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Adress not found with id someRandomId", e.getMessage());
		}
	}
	
	@Test
	public void update5() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id(SOME_RANDOM_ID).build();
			State state = State.builder().name("SP").id(SOME_RANDOM_ID).country(country).build();
			Adress adress = Adress.builder().id(SOME_RANDOM_ID).streetName(null).number("3000").neighbourhood("Bela Vista").city("São Paulo").state(state).zipcode("00000000").build();
			Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(adress));
			service.update(adress);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to update adress: Street name can not be null", e.getMessage());
		}
	}
	
	@Test
	public void update6() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id(SOME_RANDOM_ID).build();
			State state = State.builder().name("SP").id(SOME_RANDOM_ID).country(country).build();
			Adress adress = Adress.builder().id(SOME_RANDOM_ID).streetName("").number("3000").neighbourhood("Bela Vista").city("São Paulo").state(state).zipcode("00000000").build();
			Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(adress));
			service.update(adress);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to update adress: Street name can not be empty", e.getMessage());
		}
	}
	
	@Test
	public void update7() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id(SOME_RANDOM_ID).build();
			State state = State.builder().name("SP").id(SOME_RANDOM_ID).country(country).build();
			Adress adress = Adress.builder().id(SOME_RANDOM_ID).streetName("Avenida Paulista").number(null).neighbourhood("Bela Vista").city("São Paulo").state(state).zipcode("00000000").build();
			Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(adress));
			service.update(adress);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to update adress: Number can not be null", e.getMessage());
		}
	}
	
	@Test
	public void update8() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id(SOME_RANDOM_ID).build();
			State state = State.builder().name("SP").id(SOME_RANDOM_ID).country(country).build();
			Adress adress = Adress.builder().id(SOME_RANDOM_ID).streetName("Avenida Paulista").number("").neighbourhood("Bela Vista").city("São Paulo").state(state).zipcode("00000000").build();
			Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(adress));
			service.update(adress);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to update adress: Number can not be empty", e.getMessage());
		}
	}
	
	@Test
	public void update9() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id(SOME_RANDOM_ID).build();
			State state = State.builder().name("SP").id(SOME_RANDOM_ID).country(country).build();
			Adress adress = Adress.builder().id(SOME_RANDOM_ID).streetName("Avenida Paulista").number("3000").neighbourhood(null).city("São Paulo").state(state).zipcode("00000000").build();
			Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(adress));
			service.update(adress);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to update adress: Neighbourhood can not be null", e.getMessage());
		}
	}
	
	@Test
	public void update10() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id(SOME_RANDOM_ID).build();
			State state = State.builder().name("SP").id(SOME_RANDOM_ID).country(country).build();
			Adress adress = Adress.builder().id(SOME_RANDOM_ID).streetName("Avenida Paulista").number("3000").neighbourhood("").city("São Paulo").state(state).zipcode("00000000").build();
			Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(adress));
			service.update(adress);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to update adress: Neighbourhood can not be empty", e.getMessage());
		}
	}
	
	@Test
	public void update11() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id(SOME_RANDOM_ID).build();
			State state = State.builder().name("SP").id(SOME_RANDOM_ID).country(country).build();
			Adress adress = Adress.builder().id(SOME_RANDOM_ID).streetName("Avenida Paulista").number("3000").neighbourhood("Bela Vista").city(null).state(state).zipcode("00000000").build();
			Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(adress));
			service.update(adress);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to update adress: City name can not be null", e.getMessage());
		}
	}
	
	@Test
	public void update12() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id(SOME_RANDOM_ID).build();
			State state = State.builder().name("SP").id(SOME_RANDOM_ID).country(country).build();
			Adress adress = Adress.builder().id(SOME_RANDOM_ID).streetName("Avenida Paulista").number("3000").neighbourhood("Bela Vista").city("").state(state).zipcode("00000000").build();
			Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(adress));
			service.update(adress);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to update adress: City name can not be empty", e.getMessage());
		}
	}
	
	@Test
	public void update13() throws TechnicalException {
		try {
			Adress adress = Adress.builder().id(SOME_RANDOM_ID).streetName("Avenida Paulista").number("3000").neighbourhood("Bela Vista").city("São Paulo").state(null).zipcode("00000000").build();
			Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(adress));
			service.update(adress);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to update adress: State can not be null", e.getMessage());
		}
	}
	
	@Test
	public void update14() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id(SOME_RANDOM_ID).build();
			State state = State.builder().name("SP").id(null).country(country).build();
			Adress adress = Adress.builder().id(SOME_RANDOM_ID).streetName("Avenida Paulista").number("3000").neighbourhood("Bela Vista").city("São Paulo").state(state).zipcode("00000000").build();
			Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(adress));
			service.update(adress);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to update adress: State id can not be null", e.getMessage());
		}
	}
	
	@Test
	public void update15() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id(SOME_RANDOM_ID).build();
			State state = State.builder().name("SP").id("").country(country).build();
			Adress adress = Adress.builder().id(SOME_RANDOM_ID).streetName("Avenida Paulista").number("3000").neighbourhood("Bela Vista").city("São Paulo").state(state).zipcode("00000000").build();
			Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(adress));
			service.update(adress);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to update adress: State id can not be empty", e.getMessage());
		}
	}
	
	@Test
	public void update16() throws TechnicalException {
		try {
			State state = State.builder().name("SP").id(SOME_RANDOM_ID).country(null).build();
			Adress adress = Adress.builder().id(SOME_RANDOM_ID).streetName("Avenida Paulista").number("3000").neighbourhood("Bela Vista").city("São Paulo").state(state).zipcode("00000000").build();
			Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(adress));
			Mockito.when(stateService.findById(SOME_RANDOM_ID)).thenReturn(state);
			service.update(adress);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to update adress: Country can not be null", e.getMessage());
		}
	}
	
	@Test
	public void update17() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id(null).build();
			State state = State.builder().name("SP").id(SOME_RANDOM_ID).country(country).build();
			Adress adress = Adress.builder().id(SOME_RANDOM_ID).streetName("Avenida Paulista").number("3000").neighbourhood("Bela Vista").city("São Paulo").state(state).zipcode("00000000").build();
			Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(adress));
			Mockito.when(stateService.findById(SOME_RANDOM_ID)).thenReturn(state);
			service.update(adress);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to update adress: Country id can not be null", e.getMessage());
		}
	}
	
	@Test
	public void update18() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id("").build();
			State state = State.builder().name("SP").id(SOME_RANDOM_ID).country(country).build();
			Adress adress = Adress.builder().id(SOME_RANDOM_ID).streetName("Avenida Paulista").number("3000").neighbourhood("Bela Vista").city("São Paulo").state(state).zipcode("00000000").build();
			Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(adress));
			Mockito.when(stateService.findById(SOME_RANDOM_ID)).thenReturn(state);
			service.update(adress);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to update adress: Country id can not be empty", e.getMessage());
		}
	}
	
	@Test
	public void update19() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id(SOME_RANDOM_ID).build();
			State state = State.builder().name("SP").id(SOME_RANDOM_ID).country(country).build();
			Adress adress = Adress.builder().id(SOME_RANDOM_ID).streetName("Avenida Paulista").number("3000").neighbourhood("Bela Vista").city("São Paulo").state(state).zipcode(null).build();
			Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(adress));
			Mockito.when(stateService.findById(SOME_RANDOM_ID)).thenReturn(state);
			service.update(adress);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to update adress: Zip Code name can not be null", e.getMessage());
		}
	}
	
	@Test
	public void update20() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id(SOME_RANDOM_ID).build();
			State state = State.builder().name("SP").id(SOME_RANDOM_ID).country(country).build();
			Adress adress = Adress.builder().id(SOME_RANDOM_ID).streetName("Avenida Paulista").number("3000").neighbourhood("Bela Vista").city("São Paulo").state(state).zipcode("").build();
			Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(adress));
			Mockito.when(stateService.findById(SOME_RANDOM_ID)).thenReturn(state);
//			GeocodeDto geoCodeDto = GeocodeDto.builder().latitude(-23.5546455d).longitude(-46.66375300000001d).build();
//			Mockito.when(googleGeocodingService.getGeocode(adress)).thenReturn(geoCodeDto);
			service.update(adress);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to update adress: Zip Code is not valid", e.getMessage());
		}
	}
	
	@Test
	public void update21() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id(SOME_RANDOM_ID).build();
			State state = State.builder().name("SP").id(SOME_RANDOM_ID).country(country).build();
			Adress adress = Adress.builder().id(SOME_RANDOM_ID).streetName("Avenida Paulista").number("3000").neighbourhood("Bela Vista").city("São Paulo").state(state).zipcode("00000-000").build();
			Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(adress));
			Mockito.when(stateService.findById(SOME_RANDOM_ID)).thenReturn(state);
			GeocodeDto geoCodeDto = GeocodeDto.builder().latitude(-23.5546455d).longitude(-46.66375300000001d).build();
			Mockito.when(googleGeocodingService.getGeocode(adress)).thenReturn(geoCodeDto);
			service.update(adress);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to update adress: Zip Code is not valid", e.getMessage());
		}
	}
	
	@Test
	public void save1() throws TechnicalException {
		Country country = Country.builder().name("Brasil").id(SOME_RANDOM_ID).build();
		State state = State.builder().name("SP").id(SOME_RANDOM_ID).country(country).build();
		Adress adress = Adress.builder().streetName("Avenida Paulista").number("3000").neighbourhood("Bela Vista").city("São Paulo").state(state).zipcode("00000000").build();
		Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(adress));
		GeocodeDto geoCodeDto = GeocodeDto.builder().latitude(-23.5546455d).longitude(-46.66375300000001d).build();
		Mockito.when(googleGeocodingService.getGeocode(adress)).thenReturn(geoCodeDto);
		Mockito.when(stateService.findById(SOME_RANDOM_ID)).thenReturn(state);
		Mockito.when(repository.save(adress)).thenReturn(adress);
		Adress result = service.save(adress);
		assertEquals(adress, result);
	}
	
	@Test
	public void save2() throws TechnicalException {
		try {
			service.save(null);
			fail();
		} catch(Exception e) {
			assertEquals("Error while trying to save adress: Full adress can not be null", e.getMessage());
		}
	}
	
	@Test
	public void save3() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id(SOME_RANDOM_ID).build();
			State state = State.builder().name("SP").id(SOME_RANDOM_ID).country(country).build();
			Adress adress = Adress.builder().id(SOME_RANDOM_ID).streetName("Avenida Paulista").number("3000").neighbourhood("Bela Vista").city("São Paulo").state(state).zipcode("00000000").build();
			Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(adress));
			GeocodeDto geoCodeDto = GeocodeDto.builder().latitude(-23.5546455d).longitude(-46.66375300000001d).build();
			Mockito.when(googleGeocodingService.getGeocode(adress)).thenReturn(geoCodeDto);
			Mockito.when(stateService.findById(SOME_RANDOM_ID)).thenReturn(state);
			Mockito.when(repository.save(adress)).thenReturn(adress);
			service.save(adress);
			fail();
		} catch(Exception e) {
			assertEquals("Error while trying to save adress: I can not save an existing andress", e.getMessage());
		}
	}
	
	@Test
	public void save5() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id(SOME_RANDOM_ID).build();
			State state = State.builder().name("SP").id(SOME_RANDOM_ID).country(country).build();
			Adress adress = Adress.builder().streetName(null).number("3000").neighbourhood("Bela Vista").city("São Paulo").state(state).zipcode("00000000").build();
			Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(adress));
			service.save(adress);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to save adress: Street name can not be null", e.getMessage());
		}
	}
	
	@Test
	public void save6() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id(SOME_RANDOM_ID).build();
			State state = State.builder().name("SP").id(SOME_RANDOM_ID).country(country).build();
			Adress adress = Adress.builder().streetName("").number("3000").neighbourhood("Bela Vista").city("São Paulo").state(state).zipcode("00000000").build();
			Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(adress));
			service.save(adress);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to save adress: Street name can not be empty", e.getMessage());
		}
	}
	
	@Test
	public void save7() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id(SOME_RANDOM_ID).build();
			State state = State.builder().name("SP").id(SOME_RANDOM_ID).country(country).build();
			Adress adress = Adress.builder().streetName("Avenida Paulista").number(null).neighbourhood("Bela Vista").city("São Paulo").state(state).zipcode("00000000").build();
			Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(adress));
			service.save(adress);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to save adress: Number can not be null", e.getMessage());
		}
	}
	
	@Test
	public void save8() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id(SOME_RANDOM_ID).build();
			State state = State.builder().name("SP").id(SOME_RANDOM_ID).country(country).build();
			Adress adress = Adress.builder().streetName("Avenida Paulista").number("").neighbourhood("Bela Vista").city("São Paulo").state(state).zipcode("00000000").build();
			Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(adress));
			service.save(adress);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to save adress: Number can not be empty", e.getMessage());
		}
	}
	
	@Test
	public void save9() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id(SOME_RANDOM_ID).build();
			State state = State.builder().name("SP").id(SOME_RANDOM_ID).country(country).build();
			Adress adress = Adress.builder().streetName("Avenida Paulista").number("3000").neighbourhood(null).city("São Paulo").state(state).zipcode("00000000").build();
			Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(adress));
			service.save(adress);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to save adress: Neighbourhood can not be null", e.getMessage());
		}
	}
	
	@Test
	public void save10() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id(SOME_RANDOM_ID).build();
			State state = State.builder().name("SP").id(SOME_RANDOM_ID).country(country).build();
			Adress adress = Adress.builder().streetName("Avenida Paulista").number("3000").neighbourhood("").city("São Paulo").state(state).zipcode("00000000").build();
			Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(adress));
			service.save(adress);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to save adress: Neighbourhood can not be empty", e.getMessage());
		}
	}
	
	@Test
	public void save11() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id(SOME_RANDOM_ID).build();
			State state = State.builder().name("SP").id(SOME_RANDOM_ID).country(country).build();
			Adress adress = Adress.builder().streetName("Avenida Paulista").number("3000").neighbourhood("Bela Vista").city(null).state(state).zipcode("00000000").build();
			Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(adress));
			service.save(adress);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to save adress: City name can not be null", e.getMessage());
		}
	}
	
	@Test
	public void save12() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id(SOME_RANDOM_ID).build();
			State state = State.builder().name("SP").id(SOME_RANDOM_ID).country(country).build();
			Adress adress = Adress.builder().streetName("Avenida Paulista").number("3000").neighbourhood("Bela Vista").city("").state(state).zipcode("00000000").build();
			Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(adress));
			service.save(adress);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to save adress: City name can not be empty", e.getMessage());
		}
	}
	
	@Test
	public void save13() throws TechnicalException {
		try {
			Adress adress = Adress.builder().streetName("Avenida Paulista").number("3000").neighbourhood("Bela Vista").city("São Paulo").state(null).zipcode("00000000").build();
			Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(adress));
			service.save(adress);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to save adress: State can not be null", e.getMessage());
		}
	}
	
	@Test
	public void save14() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id(SOME_RANDOM_ID).build();
			State state = State.builder().name("SP").id(null).country(country).build();
			Adress adress = Adress.builder().streetName("Avenida Paulista").number("3000").neighbourhood("Bela Vista").city("São Paulo").state(state).zipcode("00000000").build();
			Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(adress));
			service.save(adress);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to save adress: State id can not be null", e.getMessage());
		}
	}
	
	@Test
	public void save15() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id(SOME_RANDOM_ID).build();
			State state = State.builder().name("SP").id("").country(country).build();
			Adress adress = Adress.builder().streetName("Avenida Paulista").number("3000").neighbourhood("Bela Vista").city("São Paulo").state(state).zipcode("00000000").build();
			Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(adress));
			service.save(adress);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to save adress: State id can not be empty", e.getMessage());
		}
	}
	
	@Test
	public void save16() throws TechnicalException {
		try {
			State state = State.builder().name("SP").id(SOME_RANDOM_ID).country(null).build();
			Adress adress = Adress.builder().streetName("Avenida Paulista").number("3000").neighbourhood("Bela Vista").city("São Paulo").state(state).zipcode("00000000").build();
			Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(adress));
			Mockito.when(stateService.findById(SOME_RANDOM_ID)).thenReturn(state);
			service.save(adress);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to save adress: Country can not be null", e.getMessage());
		}
	}
	
	@Test
	public void save17() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id(null).build();
			State state = State.builder().name("SP").id(SOME_RANDOM_ID).country(country).build();
			Adress adress = Adress.builder().streetName("Avenida Paulista").number("3000").neighbourhood("Bela Vista").city("São Paulo").state(state).zipcode("00000000").build();
			Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(adress));
			Mockito.when(stateService.findById(SOME_RANDOM_ID)).thenReturn(state);
			service.save(adress);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to save adress: Country id can not be null", e.getMessage());
		}
	}
	
	@Test
	public void save18() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id("").build();
			State state = State.builder().name("SP").id(SOME_RANDOM_ID).country(country).build();
			Adress adress = Adress.builder().streetName("Avenida Paulista").number("3000").neighbourhood("Bela Vista").city("São Paulo").state(state).zipcode("00000000").build();
			Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(adress));
			Mockito.when(stateService.findById(SOME_RANDOM_ID)).thenReturn(state);
			service.save(adress);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to save adress: Country id can not be empty", e.getMessage());
		}
	}
	
	@Test
	public void save19() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id(SOME_RANDOM_ID).build();
			State state = State.builder().name("SP").id(SOME_RANDOM_ID).country(country).build();
			Adress adress = Adress.builder().streetName("Avenida Paulista").number("3000").neighbourhood("Bela Vista").city("São Paulo").state(state).zipcode(null).build();
			Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(adress));
			Mockito.when(stateService.findById(SOME_RANDOM_ID)).thenReturn(state);
			service.save(adress);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to save adress: Zip Code name can not be null", e.getMessage());
		}
	}
	
	@Test
	public void save20() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id(SOME_RANDOM_ID).build();
			State state = State.builder().name("SP").id(SOME_RANDOM_ID).country(country).build();
			Adress adress = Adress.builder().streetName("Avenida Paulista").number("3000").neighbourhood("Bela Vista").city("São Paulo").state(state).zipcode("").build();
			Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(adress));
			Mockito.when(stateService.findById(SOME_RANDOM_ID)).thenReturn(state);
//			GeocodeDto geoCodeDto = GeocodeDto.builder().latitude(-23.5546455d).longitude(-46.66375300000001d).build();
//			Mockito.when(googleGeocodingService.getGeocode(adress)).thenReturn(geoCodeDto);
			service.save(adress);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to save adress: Zip Code is not valid", e.getMessage());
		}
	}
	
	@Test
	public void save21() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id(SOME_RANDOM_ID).build();
			State state = State.builder().name("SP").id(SOME_RANDOM_ID).country(country).build();
			Adress adress = Adress.builder().streetName("Avenida Paulista").number("3000").neighbourhood("Bela Vista").city("São Paulo").state(state).zipcode("00000-000").build();
			Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(adress));
			Mockito.when(stateService.findById(SOME_RANDOM_ID)).thenReturn(state);
			GeocodeDto geoCodeDto = GeocodeDto.builder().latitude(-23.5546455d).longitude(-46.66375300000001d).build();
			Mockito.when(googleGeocodingService.getGeocode(adress)).thenReturn(geoCodeDto);
			service.save(adress);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to save adress: Zip Code is not valid", e.getMessage());
		}
	}
	
	@Test
	public void delete1() throws TechnicalException {
		Country country = Country.builder().name("Brasil").id(SOME_RANDOM_ID).build();
		State state = State.builder().name("SP").id(SOME_RANDOM_ID).country(country).build();
		Adress adress = Adress.builder().id(SOME_RANDOM_ID).streetName("Avenida Paulista").number("3000").neighbourhood("Bela Vista").city("São Paulo").state(state).zipcode("00000000").build();
		Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(adress));
		GeocodeDto geoCodeDto = GeocodeDto.builder().latitude(-23.5546455d).longitude(-46.66375300000001d).build();
		Mockito.when(googleGeocodingService.getGeocode(adress)).thenReturn(geoCodeDto);
		Mockito.when(stateService.findById(SOME_RANDOM_ID)).thenReturn(state);
		Mockito.when(repository.save(adress)).thenReturn(adress);
		service.delete(adress);
	}
	
	@Test
	public void delete2() throws TechnicalException {
		try {
			service.delete(null);
		} catch(Exception e) {
			assertEquals("Error while trying to delete adress: full Adress can not be null", e.getMessage());
		}
	}
	
	@Test
	public void delete3() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id(SOME_RANDOM_ID).build();
			State state = State.builder().name("SP").id(SOME_RANDOM_ID).country(country).build();
			Adress adress = Adress.builder().streetName("Avenida Paulista").number("3000").neighbourhood("Bela Vista").city("São Paulo").state(state).zipcode("00000000").build();
			Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(adress));
			GeocodeDto geoCodeDto = GeocodeDto.builder().latitude(-23.5546455d).longitude(-46.66375300000001d).build();
			Mockito.when(googleGeocodingService.getGeocode(adress)).thenReturn(geoCodeDto);
			Mockito.when(stateService.findById(SOME_RANDOM_ID)).thenReturn(state);
			Mockito.when(repository.save(adress)).thenReturn(adress);
			service.delete(adress);
		} catch(Exception e) {
			assertEquals("Error while trying to delete adress: I can not delete an andress withoud and ID", e.getMessage());
		}
	}

	@Test
	public void delete4() throws TechnicalException {
		try {
			Adress adress = Adress.builder().id(SOME_RANDOM_ID).build();
			Mockito.when(repository.findById(SOME_RANDOM_ID)).thenReturn(Optional.ofNullable(null));
			service.delete(adress);
		} catch(Exception e) {
			assertEquals("Adress not found with id someRandomId", e.getMessage());
		}
	}
}

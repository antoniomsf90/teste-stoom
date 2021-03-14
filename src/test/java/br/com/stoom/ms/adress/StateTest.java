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
import br.com.stoom.ms.adress.model.Country;
import br.com.stoom.ms.adress.model.State;
import br.com.stoom.ms.adress.repository.StateRepository;
import br.com.stoom.ms.adress.service.CountryService;
import br.com.stoom.ms.adress.service.StateService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StateTest {
	@InjectMocks
	private StateService service;
	
	@Mock
	private CountryService countryService;
	
	@Mock
	private StateRepository repository;
	
	@Test
	public void salvar1() throws TechnicalException {
		Country country = Country.builder().name("Brasil").id("someRandomId").build();
		State state = State.builder().country(country).name("Sao Paulo").build();
		Mockito.when(repository.save(state)).thenReturn(state);
		Mockito.when(countryService.findById("someRandomId")).thenReturn(country);
		State result = service.save(state);
		assertEquals(result.getName(), result.getName());
		assertEquals(result.getCountry(), result.getCountry());
	}
	
	@Test
	public void salvar2() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").build();
			State state = State.builder().country(country).name("Sao Paulo").id("someRandomId").build();
			Mockito.when(repository.save(state)).thenReturn(state);
			service.save(state);
			fail();
		} catch (TechnicalException e) {
			assertEquals("Error while trying to save state: I can not save an existing state", e.getMessage());
		}
	}
	
	@Test
	public void salvar3() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id("someRandomId").build();
			State state = State.builder().country(country).name(null).build();
			Mockito.when(repository.save(state)).thenReturn(state);
			service.save(state);
			fail();
		} catch (TechnicalException e) {
			assertEquals("Error while trying to save state: State name can not be null", e.getMessage());
		}
	}
	
	@Test
	public void salvar4() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").build();
			State state = State.builder().country(country).name("").build();
			Mockito.when(repository.save(state)).thenReturn(state);
			service.save(state);
			fail();
		} catch (TechnicalException e) {
			assertEquals("Error while trying to save state: State name can not be empty", e.getMessage());
		}
	}
	
	@Test
	public void salvar5() throws TechnicalException {
		try {
			State state = State.builder().name("Sao Paulo").build();
			Mockito.when(repository.save(state)).thenReturn(state);
			service.save(state);
			fail();
		} catch (TechnicalException e) {
			assertEquals("Error while trying to save state: Country can not be null", e.getMessage());
		}
	}
	
	@Test
	public void salvar6() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").build();
			State state = State.builder().country(country).name("Sao Paulo").build();
			Mockito.when(repository.save(state)).thenReturn(state);
			service.save(state);
			fail();
		} catch (TechnicalException e) {
			assertEquals("Error while trying to save state: Country id can not be null", e.getMessage());
		}
	}
	
	@Test
	public void salvar7() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id("").build();
			State state = State.builder().country(country).name("Sao Paulo").build();
			Mockito.when(repository.save(state)).thenReturn(state);
			service.save(state);
			fail();
		} catch (TechnicalException e) {
			assertEquals("Error while trying to save state: Country id can not be empty", e.getMessage());
		}
	}
	
	@Test
	public void salvar8() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id("someRandomId").build();
			State state = State.builder().country(country).name("Sao Paulo").build();
			Mockito.when(repository.save(state)).thenReturn(state);
			Mockito.when(countryService.findById("someRandomId")).thenThrow(new TechnicalException("Error while trying to find country: Country not found with id someRandomId"));
			service.save(state);
			fail();
		} catch (TechnicalException e) {
			assertEquals("Error while trying to find country: Country not found with id someRandomId", e.getMessage());
		}
	}
	
	@Test
	public void update1() throws TechnicalException {
		Country country = Country.builder().name("Brasil").id("someRandomId").build();
		State state = State.builder().country(country).id("someRandomId").name("Sao Paulo").build();
		Mockito.when(repository.save(state)).thenReturn(state);
		Mockito.when(countryService.findById("someRandomId")).thenReturn(country);
		Mockito.when(repository.findById("someRandomId")).thenReturn(Optional.of(state));
		State result = service.update(state);
		assertEquals(result.getName(), result.getName());
		assertEquals(result.getCountry(), result.getCountry());
	}
	
	@Test
	public void update2() throws TechnicalException {
		try {
			service.update(null);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to update state: State can not be null", e.getMessage());
		}
	}
	
	@Test
	public void update3() throws TechnicalException {
		try {
			Country country = Country.builder().id("someRandomId").name("Brasil").build();
			State state = State.builder().country(country).name("Sao Paulo").build();
			Mockito.when(repository.save(state)).thenReturn(state);
			Mockito.when(countryService.findById("someRandomId")).thenReturn(country);
			Mockito.when(repository.findById("someRandomId")).thenReturn(Optional.ofNullable(state));
			service.update(state);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to update state: I can not update an state withoud and ID", e.getMessage());
		}
	}
	
	@Test
	public void update4() throws TechnicalException {
		try {
			Country country = Country.builder().id("someRandomId").name("Brasil").build();
			State state = State.builder().country(country).id("someRandomId").name("Sao Paulo").build();
			Mockito.when(repository.save(state)).thenReturn(state);
			Mockito.when(countryService.findById("someRandomId")).thenReturn(country);
			Mockito.when(repository.findById("someRandomId")).thenReturn(Optional.ofNullable(null));
			service.update(state);
			fail();
		} catch(TechnicalException e) {
			assertEquals("State not found with id someRandomId", e.getMessage());
		}
	}
	
	@Test
	public void update5() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id("someRandomId").build();
			State state = State.builder().country(country).id("someRandomId").name(null).build();
			Mockito.when(repository.save(state)).thenReturn(state);
			Mockito.when(countryService.findById("someRandomId")).thenReturn(country);
			Mockito.when(repository.findById("someRandomId")).thenReturn(Optional.of(state));
			service.update(state);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to update state: State name can not be null", e.getMessage());
		}
	}
	
	@Test
	public void update6() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id("someRandomId").build();
			State state = State.builder().country(country).id("someRandomId").name("").build();
			Mockito.when(repository.save(state)).thenReturn(state);
			Mockito.when(countryService.findById("someRandomId")).thenReturn(country);
			Mockito.when(repository.findById("someRandomId")).thenReturn(Optional.of(state));
			service.update(state);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to update state: State name can not be empty", e.getMessage());
		}
	}
	
	@Test
	public void update7() throws TechnicalException {
		try {
			State state = State.builder().id("someRandomId").name("Sao Paulo").build();
			Mockito.when(repository.save(state)).thenReturn(state);
			Mockito.when(repository.findById("someRandomId")).thenReturn(Optional.of(state));
			service.update(state);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to update state: Country can not be null", e.getMessage());
		}
	}
	
	@Test
	public void update8() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").build();
			State state = State.builder().country(country).id("someRandomId").name("Sao Paulo").build();
			Mockito.when(repository.save(state)).thenReturn(state);
			Mockito.when(countryService.findById("someRandomId")).thenReturn(country);
			Mockito.when(repository.findById("someRandomId")).thenReturn(Optional.of(state));
			service.update(state);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to update state: Country id can not be null", e.getMessage());
		}
	}
	
	@Test
	public void update9() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id("someRandomId").build();
			State state = State.builder().country(country).id("someRandomId").name("Sao Paulo").build();
			Mockito.when(repository.save(state)).thenReturn(state);
			Mockito.when(countryService.findById("someRandomId")).thenThrow(new TechnicalException("Error while trying to find country: Country not found with id someRandomId"));
			Mockito.when(repository.findById("someRandomId")).thenReturn(Optional.of(state));
			service.update(state);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to find country: Country not found with id someRandomId", e.getMessage());
		}
	}
	
	@Test
	public void delete1() throws TechnicalException {
		Country country = Country.builder().name("Brasil").id("someRandomId").build();
		State state = State.builder().country(country).id("someRandomId").name("Sao Paulo").build();
		Mockito.when(repository.findById("someRandomId")).thenReturn(Optional.of(state));
		service.delete(state);
	}
	
	@Test
	public void delete2() throws TechnicalException {
		try {
			service.delete(null);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to delete state : full state can not be null", e.getMessage());
		}
	}
	
	@Test
	public void delete3() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id("someRandomId").build();
			State state = State.builder().country(country).name("Sao Paulo").build();
			Mockito.when(repository.findById("someRandomId")).thenReturn(Optional.of(state));
			service.delete(state);
			fail();
		} catch(TechnicalException e) {
			assertEquals("Error while trying to delete state : I can not delete an state withoud and ID", e.getMessage());
		}
	}
	
	@Test
	public void delete4() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id("someRandomId").build();
			State state = State.builder().country(country).id("someRandomId").name("Sao Paulo").build();
			Mockito.when(repository.findById("someRandomId")).thenReturn(Optional.ofNullable(null));
			service.delete(state);
			fail();
		} catch(TechnicalException e) {
			assertEquals("State not found with id someRandomId", e.getMessage());
		}
	}
	
	@Test
	public void findById1() throws TechnicalException {
		String id = "someRandomId";
		Country country = Country.builder().name("Brasil").id("someRandomId").build();
		State state = State.builder().country(country).id("someRandomId").name("Sao Paulo").build();
		Mockito.when(repository.findById(id)).thenReturn(Optional.of(state));
		State result = service.findById(id);
		assertEquals(result, state);
	}
	
	@Test
	public void findById2() throws TechnicalException {
		try {
			String id = "someRandomId";
			Mockito.when(repository.findById(id)).thenReturn(Optional.ofNullable(null));
			service.findById(id);
			fail();
		} catch(Exception e) {
			assertEquals("State not found with id someRandomId", e.getMessage());
		}
	}
	
	@Test
	public void findAll() throws TechnicalException {
		Mockito.when(repository.findAll()).thenReturn(Lists.newArrayList());
		List<State> states = service.findAll();
		assertEquals(0, states.size());
	}
	
	@Test
	public void findAll2() throws TechnicalException {
		List<State> founds = Lists.newArrayList();
		Country country = Country.builder().name("Brasil").id("someRandomId").build();
		State state = State.builder().country(country).id("someRandomId").name("Sao Paulo").build();
		founds.add(state);
		Mockito.when(repository.findAll()).thenReturn(founds);
		List<State> states = service.findAll();
		assertEquals(1, states.size());
	}
}

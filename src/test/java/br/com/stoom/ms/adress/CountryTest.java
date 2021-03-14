package br.com.stoom.ms.adress;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.stoom.ms.adress.exception.TechnicalException;
import br.com.stoom.ms.adress.model.Country;
import br.com.stoom.ms.adress.repository.CountryRepository;
import br.com.stoom.ms.adress.service.CountryService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CountryTest {

	@InjectMocks
	private CountryService service;

	@Mock
	private CountryRepository repository;

	@Test
	public void salvar1() throws TechnicalException {
		Country country = Country.builder().name("Brasil").build();
		Mockito.when(repository.save(country)).thenReturn(country);
		Country result = service.save(country);
		assertEquals(result.getName(), result.getName());
	}

	@Test
	public void salvar2() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id("someRandomId").build();
			service.save(country);
			fail();
		} catch (TechnicalException e) {
			assertEquals("Error while trying to save country: I can not save an existing country", e.getMessage());
		}
	}

	@Test
	public void salvar3() throws TechnicalException {
		try {
			Country country = Country.builder().name(null).build();
			service.save(country);
			fail();
		} catch (TechnicalException e) {
			assertEquals("Error while trying to save country: Country name can not be null", e.getMessage());
		}
	}

	@Test
	public void salvar4() throws TechnicalException {
		try {
			Country country = Country.builder().name("").build();
			service.save(country);
			fail();
		} catch (TechnicalException e) {
			assertEquals("Error while trying to save country: Country name can not be empty", e.getMessage());
		}
	}

	@Test
	public void update1() throws TechnicalException {
		Country country = Country.builder().name("Brasil").id("someRandomId").build();
		Mockito.when(repository.save(country)).thenReturn(country);
		Mockito.when(repository.findById("someRandomId")).thenReturn(Optional.ofNullable(country));
		Country result = service.update(country);
		assertEquals(result.getName(), result.getName());
	}

	@Test
	public void update2() throws TechnicalException {
		try {
			Country country = null;
			Mockito.when(repository.save(country)).thenReturn(country);
			service.update(country);
			fail();
		} catch (TechnicalException e) {
			assertEquals("Error while trying to update country: Country can not be null", e.getMessage());
		}
	}

	@Test
	public void update3() throws TechnicalException {
		try {
			Country country = Country.builder().build();
			Mockito.when(repository.save(country)).thenReturn(country);
			service.update(country);
			fail();
		} catch (TechnicalException e) {
			assertEquals("Error while trying to update country: I can not update an country withoud and ID",
					e.getMessage());
		}
	}

	@Test
	public void update4() throws TechnicalException {
		try {
			Country country = Country.builder().id("someRandomId").build();
			Mockito.when(repository.save(country)).thenReturn(country);
			Mockito.when(repository.findById("someRandomId")).thenReturn(Optional.ofNullable(null));
			service.update(country);
			fail();
		} catch (TechnicalException e) {
			assertEquals("Error while trying to find country: Country not found with id someRandomId", e.getMessage());
		}
	}

	@Test
	public void update5() throws TechnicalException {
		try {
			Country country = Country.builder().id("someRandomId").build();
			Mockito.when(repository.save(country)).thenReturn(country);
			Mockito.when(repository.findById("someRandomId")).thenReturn(Optional.of(country));
			service.update(country);
			fail();
		} catch (TechnicalException e) {
			assertEquals("Error while trying to update country: Country name can not be null", e.getMessage());
		}
	}

	@Test
	public void update6() throws TechnicalException {
		try {
			Country country = Country.builder().id("someRandomId").name("").build();
			Mockito.when(repository.save(country)).thenReturn(country);
			Mockito.when(repository.findById("someRandomId")).thenReturn(Optional.of(country));
			service.update(country);
			fail();
		} catch (TechnicalException e) {
			assertEquals("Error while trying to update country: Country name can not be empty", e.getMessage());
		}
	}

	@Test
	public void delete1() throws TechnicalException {
		Country country = Country.builder().id("someRandomId").name("Brasil").build();
		Mockito.when(repository.findById("someRandomId")).thenReturn(Optional.of(country));
		service.delete(country);
	}

	@Test
	public void delete2() throws TechnicalException {
		try {
			Country country = null;
			Mockito.when(repository.findById("someRandomId")).thenReturn(Optional.ofNullable(country));
			service.delete(country);
			fail();
		} catch (TechnicalException e) {
			assertEquals("Error while trying to update country: full country can not be null", e.getMessage());
		}
	}

	@Test
	public void delete3() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").build();
			Mockito.when(repository.save(country)).thenReturn(country);
			Mockito.when(repository.findById("someRandomId")).thenReturn(Optional.of(country));
			service.delete(country);
			fail();
		} catch (TechnicalException e) {
			assertEquals("Error while trying to update country: I can not delete an country withoud and ID",
					e.getMessage());
		}
	}

	@Test
	public void delete4() throws TechnicalException {
		try {
			Country country = Country.builder().name("Brasil").id("someRandomId").build();
			Mockito.when(repository.save(country)).thenReturn(country);
			Mockito.when(repository.findById("someRandomId")).thenReturn(Optional.ofNullable(null));
			service.delete(country);
			fail();
		} catch (TechnicalException e) {
			assertEquals("Error while trying to find country: Country not found with id someRandomId", e.getMessage());
		}
	}

	@Test
	public void findById1() throws TechnicalException {
		String id = "someRandomId";
		Country country = Country.builder().name("Brasil").id("someRandomId").build();
		Mockito.when(repository.findById(id)).thenReturn(Optional.of(country));
		Country result = service.findById(id);
		assertEquals(result, country);
	}
	
	@Test
	public void findById2() throws TechnicalException {
		String id = "someRandomId";
		Country country = Country.builder().name("Brasil").id("someRandomId").build();
		Mockito.when(repository.findById(id)).thenReturn(Optional.of(country));
		Country result = service.findById(id);
		assertEquals(result, country);
	}
}

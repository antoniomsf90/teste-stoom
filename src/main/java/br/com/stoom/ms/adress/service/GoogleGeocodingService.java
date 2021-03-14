package br.com.stoom.ms.adress.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import br.com.stoom.ms.adress.google.geocoding.dto.GeocodingResponse;
import br.com.stoom.ms.adress.model.Adress;
import br.com.stoom.ms.adress.model.GeocodeDto;

@Service
public class GoogleGeocodingService {
	
	@Value("${br.com.stoom.ms.adress.google.apikey}")
	private String API_KEY;
	
	@Value("${br.com.stoom.ms.adress.google.url}")
	private String URL;
	
	private String buildAdressSearch(Adress adress) {
		return adress.getNumber()+", "+adress.getStreetName()+", "+adress.getCity()+", "+adress.getState().getName()+", "+adress.getState().getCountry().getName();
	}
	
	public GeocodeDto getGeocode(Adress adress) {
		String url = URL.replace("{{adress}}", this.buildAdressSearch(adress));
		url = url.replace("{{apikey}}", API_KEY);
		RestTemplate rt = new RestTemplate() ;
		ResponseEntity<GeocodingResponse> response = rt.getForEntity(url, GeocodingResponse.class);
		Assert.isTrue(!HttpStatus.ACCEPTED.equals(response.getStatusCode()), "error while tryng to request geocoding. http status: "+response.getStatusCodeValue());
		Assert.notEmpty(response.getBody().getResults(), "adress not found in google geocoding");
		Assert.isTrue(response.getBody().getResults().size()==1, "found more than one adress in google geocoding");
		double latitude = response.getBody().getResults().get(0).getGeometry().getLocation().getLat();
		double longitude = response.getBody().getResults().get(0).getGeometry().getLocation().getLng();
		return GeocodeDto.builder().latitude(latitude).longitude(longitude).build();
	}
}

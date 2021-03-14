package br.com.stoom.ms.adress.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GeocodeDto {
	private Double latitude;
	private Double longitude;
}

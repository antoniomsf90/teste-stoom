package br.com.stoom.ms.adress.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.querydsl.core.annotations.QueryEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@QueryEntity
@Document
@Getter
@Setter
@Builder
public class Adress {
	@Id
	private String id;
	
	private String streetName;
	
	private String number;
	
	private String complement;
	
	private String neighbourhood;
	
	private String city;
	
	@DBRef
	private State state;
	
	private String zipcode;
	
	private Double latitude;
	
	private Double longitude;
	
}

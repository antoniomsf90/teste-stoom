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
public class State {
	@Id
	private String id;
	
	private String name;
	
	@DBRef
	private Country country;
}

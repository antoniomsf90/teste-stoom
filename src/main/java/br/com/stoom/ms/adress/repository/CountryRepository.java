package br.com.stoom.ms.adress.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.stoom.ms.adress.model.Country;

public interface CountryRepository extends MongoRepository<Country, String> {

}

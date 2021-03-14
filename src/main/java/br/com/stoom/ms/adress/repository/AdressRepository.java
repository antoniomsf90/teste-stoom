package br.com.stoom.ms.adress.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.stoom.ms.adress.model.Adress;

public interface AdressRepository extends MongoRepository<Adress, String> {

}

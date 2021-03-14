package br.com.stoom.ms.adress.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.stoom.ms.adress.model.State;

public interface StateRepository extends MongoRepository<State, String> {

}

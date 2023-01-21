package com.MongoDb.Joc.repository;

import com.MongoDb.Joc.Model.Jugador;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JugadorRepository extends MongoRepository<Jugador, Integer> {
	boolean existsByNom(String nom);
	
}

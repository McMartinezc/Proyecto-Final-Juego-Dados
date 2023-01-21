package com.MongoDb.Joc.repository;

import com.MongoDb.Joc.Model.Jugador;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JugadorRepository extends MongoRepository<Jugador, Integer> {
	boolean existsByUsername(String username);
	Optional<Jugador> findByUsername (String username);
	
}

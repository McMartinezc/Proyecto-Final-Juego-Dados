package com.MongoDb.Joc.repository;

import java.util.List;

import com.MongoDb.Joc.Model.Tirada;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TiradaRepository extends MongoRepository<Tirada, Integer> {

	List<Tirada> getTiradasByIdjugador(int player_id);
}

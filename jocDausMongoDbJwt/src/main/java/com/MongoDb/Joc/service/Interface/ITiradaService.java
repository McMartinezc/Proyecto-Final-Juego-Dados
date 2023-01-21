package com.MongoDb.Joc.service.Interface;

import java.util.List;

import com.MongoDb.Joc.Model.Tirada;

public interface ITiradaService {

	Tirada jugadorTiraDaus(int player_id);
	List<Tirada> getTiradesByJugador(int player_id);
	void borraTirades(int player_id) throws Exception;

	
}

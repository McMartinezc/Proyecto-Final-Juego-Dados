package com.MongoDb.Joc.service.Interface;

import java.util.List;

import com.MongoDb.Joc.Model.Tirada;

public interface ITiradaService {

	Tirada jugadorTiraDaus(String user_id);
	List<Tirada> getTiradesByUser(String user_id);
	void borraTirades(String user_id) throws Exception;

	
}

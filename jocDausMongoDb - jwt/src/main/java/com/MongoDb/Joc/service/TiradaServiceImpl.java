package com.MongoDb.Joc.service;

import java.util.List;
import java.util.Optional;

import com.MongoDb.Joc.Model.User;
import com.MongoDb.Joc.service.Interface.ITiradaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.MongoDb.Joc.Model.Tirada;
import com.MongoDb.Joc.repository.UserRepository;
import com.MongoDb.Joc.repository.TiradaRepository;

@Service
public class TiradaServiceImpl implements ITiradaService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TiradaRepository tiradaRepository;

	@Override
	public Tirada jugadorTiraDaus(String player_id) {

		Optional<User> jugador = userRepository.findById(player_id);

		if (jugador.isPresent()) {
			User user1 = jugador.get();
			Tirada tirada = new Tirada(user1.getId());
			user1.addTirada(tirada);
			userRepository.save(user1);
			tiradaRepository.save(tirada);
			return tirada;
		}
		return null;
	}

	@Override
	public List<Tirada> getTiradesByUser(String user_id) {
		return tiradaRepository.getTiradasByIdUser(user_id);
	}

	@Override
	public void borraTirades (String user_id) throws Exception {
		Optional<User> optionalJugador = userRepository.findById(user_id);
		if (optionalJugador.isPresent()) {
			User user = optionalJugador.get();
			tiradaRepository.deleteAll(user.getMisTiradas());
			user.getMisTiradas().clear();
			userRepository.save(user);
		} else {
			throw new Exception("El jugador amb id " + user_id + " no existeix!");
		}
	}

}

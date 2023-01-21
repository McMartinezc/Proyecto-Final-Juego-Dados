package com.MongoDb.Joc.service;

import java.util.List;
import java.util.Optional;

import com.MongoDb.Joc.service.Interface.ITiradaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.MongoDb.Joc.Model.Jugador;
import com.MongoDb.Joc.Model.Tirada;
import com.MongoDb.Joc.repository.JugadorRepository;
import com.MongoDb.Joc.repository.TiradaRepository;

@Service
public class TiradaServiceImpl implements ITiradaService {

	@Autowired
	private JugadorRepository jugadorRepository;

	@Autowired
	private TiradaRepository tiradaRepository;
	
	@Autowired
	private SequenceGeneratorService generatorJugadorService;

	@Override
	public Tirada jugadorTiraDaus(int player_id) {

		Optional<Jugador> jugador = jugadorRepository.findById(player_id);

		if (jugador.isPresent()) {
			Jugador jugador1 = jugador.get();
			Tirada tirada = new Tirada(jugador1.getId());
			tirada.setId(generatorJugadorService.getSequenceNumber(Tirada.SEQUENCE_NAME));
			jugador1.addTirada(tirada);
			jugadorRepository.save(jugador1);
			tiradaRepository.save(tirada);
			return tirada;
		}
		return null;
	}

	@Override
	public List<Tirada> getTiradesByJugador(int player_id) {
		return tiradaRepository.getTiradasByIdjugador(player_id);
	}

	@Override
	public void borraTirades (int player_id) throws Exception {
		Optional<Jugador> optionalJugador = jugadorRepository.findById(player_id);
		if (optionalJugador.isPresent()) {
			Jugador jugador = optionalJugador.get();
			tiradaRepository.deleteAll(jugador.getMisTiradas());
			jugador.getMisTiradas().clear();
			jugadorRepository.save(jugador);
		} else {
			throw new Exception("El jugador amb id " + player_id + " no existeix!");
		}
	}

}

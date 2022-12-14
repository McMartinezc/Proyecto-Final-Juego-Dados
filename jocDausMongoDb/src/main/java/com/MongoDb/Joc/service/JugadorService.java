package com.MongoDb.Joc.service;

import java.util.*;

import com.MongoDb.Joc.Exception.AlreadyExist;
import com.MongoDb.Joc.Model.Jugador;
import com.MongoDb.Joc.Model.Tirada;
import com.MongoDb.Joc.repository.JugadorRepository;
import com.MongoDb.Joc.repository.TiradaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JugadorService {

	//variable per no tenir noms anónims repetits
	private static int contador = 1; 
	
	@Autowired
	private JugadorRepository jugadorRepository;

	@Autowired
	private TiradaRepository tiradaRepository;

	@Autowired
	private SequenceGeneratorService generatorJugadorService;

	//METODES CRUD

	public List<Jugador> getAllJugadors() {
		List<Jugador> llista = jugadorRepository.findAll();
		return llista;
	}


	public Optional<Jugador> getJugadorById(int id) {
		return jugadorRepository.findById(id);
	}


	public boolean existeNomJugador(Jugador jugador) {
		return jugadorRepository.existsByNom(jugador.getNom());
	}


	public Jugador saveJugador(Jugador jugador) throws Exception {

		if (jugador.getNom() == null || "".equals(jugador.getNom())) {
			jugador.setNom("Anonim");
		}
		if (jugadorRepository.existsByNom(jugador.getNom())) {
			throw new Exception("Jugador amb nom: " + jugador.getNom() + " ja existeix");
		}
		if (jugador.getNom().equalsIgnoreCase("anonim")) {
			jugador.setNom(jugador.getNom() + contador);
			contador++;
		}
		jugador.setId(generatorJugadorService.getSequenceNumber(Jugador.SEQUENCE_NAME));
		return jugadorRepository.save(jugador);
	}


	public Jugador updateJugador(int id, Jugador jugador) {

		if (jugadorRepository.existsByNom(jugador.getNom())) {
			throw new AlreadyExist("El nom del jugador ja existeix");
		}

		Optional<Jugador> userOptional = jugadorRepository.findById(id);
		userOptional.get().setNom(jugador.getNom());

		//Guardem al repository
		return jugadorRepository.save(userOptional.get());

	}

	//METODES PERCENTATGES JUGADORS

	//Retorna el llistat de jugadors amb el seu percentatge d'exits per mostrar per pantalla ordenat per orde alfabetic(TreeMap)
	public TreeMap<String, Double> llistatRankingJugadors(){

		//Creem llistat de tots el jugadors del repository
		List<Jugador> llistatJugadors = jugadorRepository.findAll();

		//Creem un Map per guardar nom de jugador i percentatge
		TreeMap<String, Double> llistatPercentatgeJugadors = new TreeMap<>();

		if(!llistatJugadors.isEmpty()){
			List<Tirada> tiradaJugadorActual;

			for(Jugador jugador : llistatJugadors){
				tiradaJugadorActual = tiradaRepository.getTiradasByIdjugador(jugador.getId());

				if(!tiradaJugadorActual.isEmpty()){
					String nomJugador = jugador.getNom();
					Double percentatge = jugador.calculaPercentatgeExitJugador();
					llistatPercentatgeJugadors.put(nomJugador, percentatge);
				}else {
					llistatPercentatgeJugadors.put(jugador.getNom(), jugador.getPercentatge());
				}
			}
		}
		return llistatPercentatgeJugadors;
	}

	//Calcula la mitja del jugadors
	public double mitjaJugadors(){
		List<Jugador> llistatJugadors = jugadorRepository.findAll();
		double mitja = llistatJugadors.stream().mapToDouble(Jugador::calculaPercentatgeExitJugador).average().orElse(0.0);
		return Math.round(mitja *100.0)/100.0;
	}

	//Llista de tots els jugadors amb el seu percentatge, per poder buscar millor i pitjor jugador
	public List<Jugador> getListJugadorsRanking (List<Jugador>llistaJugadors){
		List<Jugador> jugadors = new ArrayList<>();
		double percentatge;

		for(Jugador user: llistaJugadors){
			percentatge = user.calculaPercentatgeExitJugador();
			user.setPercentatge(percentatge);
			jugadors.add(user);
			jugadorRepository.save(user);
		}
		return jugadors;
	}


	public Jugador jugadorLoser() {
		List<Jugador> llistatJugadors  = jugadorRepository.findAll();
		List<Jugador> jugadors  = getListJugadorsRanking(llistatJugadors);
		jugadors.sort(Comparator.comparing(Jugador::calculaPercentatgeExitJugador));
		return jugadors .get(0);
	}


	public Jugador jugadorBest() {
		List<Jugador> llistatJugadors  = jugadorRepository.findAll();
		List<Jugador> jugadors  = getListJugadorsRanking(llistatJugadors);
		jugadors.sort(Comparator.comparing(Jugador::calculaPercentatgeExitJugador).reversed());
		return jugadors .get(0);
	}

}

package com.MongoDb.Joc.controller;

import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

import com.MongoDb.Joc.Dto.UserDto;
import com.MongoDb.Joc.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MongoDb.Joc.service.UserService;

@RestController
@RequestMapping("/players")
public class UserController {

	@Autowired
	private UserService userService;

	//Mostra tots els jugadors
	@GetMapping("/getAll")
	public ResponseEntity<List<User>> getAll() {
		try {
			List<User> jugadores = userService.getAllJugadors();
			return new ResponseEntity<>(jugadores, HttpStatus.FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	//Buscar un jugador
	@GetMapping("/getOne/{id}")
	public ResponseEntity<User> getOne (@PathVariable("id") String id) {

		try {
			Optional<User> jugador = userService.getJugadorById(id);
			if (jugador.isPresent()) {
				return new ResponseEntity<>(jugador.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//Update jugador
	@PutMapping("/update/{id}")
	public ResponseEntity<UserDto> updateJugador(@PathVariable("id") String id, @RequestBody UserDto userDto) {
		return new ResponseEntity<>(userService.updateJugador(id, userDto), HttpStatus.CREATED);
	}


	//LLISTATS JUGADORS

	//GET /players/ranking: retorna el ranking de tots els jugadors/es del sistema.
	@GetMapping("/ranking")
	public ResponseEntity<TreeMap <String, Double>> getLlistatRankingMigJugadors(){
		return new ResponseEntity<>(userService.llistatRankingJugadors(), HttpStatus.OK);
	}
	//GET /players/: retorna el percentatge mitjà d’èxits de tots els jugadors.
	@GetMapping("/")
	public ResponseEntity<Double> getPercentatgeMitja(){
		double percentatgeExit= userService.mitjaJugadors();
		return new ResponseEntity<>(percentatgeExit, HttpStatus.OK);
	}

	//GET /players/ranking/loser: retorna el jugador/a amb pitjor percentatge d’èxit.
	@GetMapping("/ranking/loser")
	public ResponseEntity<UserDto> getJugadorLoser(){
		return new ResponseEntity<>(userService.jugadorLoser(), HttpStatus.OK);
	}

	//GET /players/ranking/loser: retorna el jugador/a amb millor percentatge d’èxit.
	@GetMapping("/ranking/winner")
	public ResponseEntity<UserDto> getJugadorBest(){
		return new ResponseEntity<>(userService.jugadorBest(), HttpStatus.OK);
	}
}

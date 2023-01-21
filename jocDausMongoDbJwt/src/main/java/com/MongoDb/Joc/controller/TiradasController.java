package com.MongoDb.Joc.controller;

import java.util.List;

import com.MongoDb.Joc.service.TiradaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MongoDb.Joc.Model.Tirada;

@RestController
@RequestMapping("/players/")
public class TiradasController {

	@Autowired
	private TiradaServiceImpl service;

	//JOC
	//Jugador tira els daus
	@PostMapping("/tiradaDaus/{id}")
	public ResponseEntity<Tirada> jugadorTiraDaus (@PathVariable("id") int id) {

		try {
			Tirada tirada = service.jugadorTiraDaus(id);
			if (tirada != null) {
				return new ResponseEntity<>(tirada, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//Borrar llistat de tirades d'un jugador
	@DeleteMapping("/deleteTirades/{id}")
	public ResponseEntity<String> deleteTiradasJugador(@PathVariable("id") int id) {

		try {
			service.borraTirades(id);
			return new ResponseEntity<>("Tirades eliminades correctament", HttpStatus.OK);

		} catch (Exception ex) {
			return new  ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	//RETORNA LLISTAT

	//Retorna el llistat de jugades d'un jugador
	@GetMapping("/tiradesJugador/{id}")
	public ResponseEntity<List<Tirada>> getTiradasByJugador(@PathVariable("id") int id) {

		try {
			if (service.getTiradesByJugador(id) != null) {
				return new ResponseEntity<>(service.getTiradesByJugador(id), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

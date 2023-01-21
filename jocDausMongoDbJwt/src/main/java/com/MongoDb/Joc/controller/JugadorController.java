package com.MongoDb.Joc.controller;

import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

import com.MongoDb.Joc.Dto.JugadorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MongoDb.Joc.Model.Jugador;
import com.MongoDb.Joc.service.JugadorService;

@RestController
@RequestMapping("/players")
public class JugadorController {

    @Autowired
    private JugadorService jugadorService;

    //Mostra tots els jugadors
    @GetMapping("/getAll")
    public ResponseEntity<List<Jugador>> getAll() {
        try {
            List<Jugador> jugadores = jugadorService.getAllJugadors();
            return new ResponseEntity<>(jugadores, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Buscar un jugador
    @GetMapping("/getOne/{id}")
    public ResponseEntity<Jugador> getOne(@PathVariable("id") int id) {

        try {
            Optional<Jugador> jugador = jugadorService.getJugadorById(id);
            if (jugador.isPresent()) {
                return new ResponseEntity<>(jugador.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PUT /players : modifica el nom del jugador
    @PutMapping("/update/{id}")
    public ResponseEntity<JugadorDto> updateJugador(@PathVariable("id") int id, @RequestBody JugadorDto jugadorDto) {
        return new ResponseEntity<>(jugadorService.updateJugador(id, jugadorDto), HttpStatus.CREATED);
    }

    //LLISTATS JUGADORS

    //GET /players/ranking: retorna el ranking de tots els jugadors/es del sistema.
    @GetMapping("/ranking")
    public ResponseEntity<TreeMap<String, Double>> getLlistatRankingMigJugadors() {
        return new ResponseEntity<>(jugadorService.llistatRankingJugadors(), HttpStatus.OK);
    }

    //GET /players/: retorna el percentatge mitjà d’èxits de tots els jugadors.
    @GetMapping("/")
    public ResponseEntity<Double> getPercentatgeMitja() {
        double percentatgeExit = jugadorService.mitjaJugadors();
        return new ResponseEntity<>(percentatgeExit, HttpStatus.OK);
    }

    //GET /players/ranking/loser: retorna el jugador/a amb pitjor percentatge d’èxit.
    @GetMapping("/ranking/loser")
    public ResponseEntity<JugadorDto> getJugadorLoser() {
        return new ResponseEntity<>(jugadorService.jugadorLoser(), HttpStatus.OK);
    }

    //GET /players/ranking/loser: retorna el jugador/a amb millor percentatge d’èxit.
    @GetMapping("/ranking/winner")
    public ResponseEntity<JugadorDto> getJugadorBest() {
        return new ResponseEntity<>(jugadorService.jugadorBest(), HttpStatus.OK);
    }
}

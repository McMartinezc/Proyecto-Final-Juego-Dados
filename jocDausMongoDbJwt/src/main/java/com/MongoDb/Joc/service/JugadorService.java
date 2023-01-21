package com.MongoDb.Joc.service;

import java.util.*;

import com.MongoDb.Joc.Dto.JugadorDto;
import com.MongoDb.Joc.Exception.AlreadyExist;
import com.MongoDb.Joc.Model.Jugador;
import com.MongoDb.Joc.Model.Tirada;
import com.MongoDb.Joc.repository.JugadorRepository;
import com.MongoDb.Joc.repository.TiradaRepository;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper modelMapper;

    //METODES CRUD

    public List<Jugador> getAllJugadors() {
        List<Jugador> llista = jugadorRepository.findAll();
        return llista;
    }


    public Optional<Jugador> getJugadorById(int id) {
        return jugadorRepository.findById(id);
    }


    public JugadorDto updateJugador(int id, JugadorDto jugadorDto) {

        if (jugadorRepository.existsByUsername(jugadorDto.getUsername())) {
            throw new AlreadyExist("El nom del jugador ja existeix");
        }
        Jugador jugador = convertDTOAEntitat(jugadorDto);

        Optional<Jugador> userOptional = jugadorRepository.findById(id);
        userOptional.get().setUsername(jugador.getUsername());

        //Guardem al repository
        jugadorRepository.save(userOptional.get());

        return convertEntitatADto(userOptional.get());

    }

    //METODES PERCENTATGES JUGADORS

    //Retorna el llistat de jugadors amb el seu percentatge d'exits per mostrar per pantalla ordenat per orde alfabetic(TreeMap)
    public TreeMap<String, Double> llistatRankingJugadors() {

        //Creem llistat de tots el jugadors del repository
        List<Jugador> llistatJugadors = jugadorRepository.findAll();

        //Creem un Map per guardar nom de jugador i percentatge
        TreeMap<String, Double> llistatPercentatgeJugadors = new TreeMap<>();

        if (!llistatJugadors.isEmpty()) {
            List<Tirada> tiradaJugadorActual;

            for (Jugador jugador : llistatJugadors) {
                tiradaJugadorActual = tiradaRepository.getTiradasByIdjugador(jugador.getId());

                if (!tiradaJugadorActual.isEmpty()) {
                    String nomJugador = jugador.getUsername();
                    Double percentatge = jugador.calculaPercentatgeExitJugador();
                    llistatPercentatgeJugadors.put(nomJugador, percentatge);
                } else {
                    llistatPercentatgeJugadors.put(jugador.getUsername(), jugador.getPercentatge());
                }
            }
        }
        return llistatPercentatgeJugadors;
    }

    //Calcula la mitja del jugadors
    public double mitjaJugadors() {
        List<Jugador> llistatJugadors = jugadorRepository.findAll();
        double mitja = llistatJugadors.stream().mapToDouble(Jugador::calculaPercentatgeExitJugador).average().orElse(0.0);
        return Math.round(mitja * 100.0) / 100.0;
    }

    //Llista de tots els jugadors amb el seu percentatge, per poder buscar millor i pitjor jugador
    public List<Jugador> getListJugadorsRanking(List<Jugador> llistaJugadors) {
        List<Jugador> jugadors = new ArrayList<>();
        double percentatge;

        for (Jugador user : llistaJugadors) {
            percentatge = user.calculaPercentatgeExitJugador();
            user.setPercentatge(percentatge);
            jugadors.add(user);
            jugadorRepository.save(user);
        }
        return jugadors;
    }

    //Retorna el pitjor jugador
    public JugadorDto jugadorLoser() {
        List<Jugador> llistatJugadors = jugadorRepository.findAll();
        List<Jugador> jugadors = getListJugadorsRanking(llistatJugadors);
        jugadors.sort(Comparator.comparing(Jugador::calculaPercentatgeExitJugador));
        return convertEntitatADto(jugadors.get(0));
    }

    //Retorna el millor jugador
    public JugadorDto jugadorBest() {
        List<Jugador> llistatJugadors = jugadorRepository.findAll();
        List<Jugador> jugadors = getListJugadorsRanking(llistatJugadors);
        jugadors.sort(Comparator.comparing(Jugador::calculaPercentatgeExitJugador));
        return convertEntitatADto(jugadors.get(jugadors.size() - 1));
    }

    //MODELMAPPERS

    //Convertim DTO a entitat utilitzan el ModelMapper
    public Jugador convertDTOAEntitat(JugadorDto jugadorDto) {
        return modelMapper.map(jugadorDto, Jugador.class);
    }

    //Convertim entitat a DTO utilitzan el ModelMapper
    public JugadorDto convertEntitatADto(Jugador jugador) {
        return modelMapper.map(jugador, JugadorDto.class);
    }
}

package com.MongoDb.Joc.service;

import java.util.*;

import com.MongoDb.Joc.Dto.UserDto;
import com.MongoDb.Joc.Exception.AlreadyExist;
import com.MongoDb.Joc.Model.User;
import com.MongoDb.Joc.Model.Tirada;
import com.MongoDb.Joc.repository.UserRepository;
import com.MongoDb.Joc.repository.TiradaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	//variable per no tenir noms anónims repetits
	private static int contador = 1; 
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TiradaRepository tiradaRepository;

	@Autowired
	private ModelMapper modelMapper;

	//METODES CRUD

	public List<User> getAllJugadors() {
		List<User> llista = userRepository.findAll();
		return llista;
	}

	public Optional<User> getJugadorById(String id) {
		return userRepository.findById(id);
	}

	public boolean existeNameUser(User user) {
		return userRepository.existsByUsername(user.getUsername());
	}

	public UserDto updateJugador(String id, UserDto userDto) {

		if (userRepository.existsByUsername(userDto.getUsername())) {
			throw new AlreadyExist("El nom del user ja existeix");
		}
		User user = convertDTOAEntitat(userDto);

		Optional<User> userOptional = userRepository.findById(id);
		userOptional.get().setUsername(user.getUsername());

		//Guardem al repository
		userRepository.save(userOptional.get());

		return convertEntitatADto(userOptional.get());

	}

	//METODES PERCENTATGES JUGADORS

	//Retorna el llistat de jugadors amb el seu percentatge d'exits per mostrar per pantalla ordenat per orde alfabetic(TreeMap)
	public TreeMap<String, Double> llistatRankingJugadors(){

		//Creem llistat de tots el jugadors del repository
		List<User> llistatUsers = userRepository.findAll();

		//Creem un Map per guardar nom de jugador i percentatge
		TreeMap<String, Double> llistatPercentatgeJugadors = new TreeMap<>();

		if(!llistatUsers.isEmpty()){
			List<Tirada> tiradaJugadorActual;

			for(User user : llistatUsers){
				tiradaJugadorActual = tiradaRepository.getTiradasByIdUser(user.getId());

				if(!tiradaJugadorActual.isEmpty()){
					String nomJugador = user.getUsername();
					Double percentatge = user.calculaPercentatgeExitJugador();
					llistatPercentatgeJugadors.put(nomJugador, percentatge);
				}else {
					llistatPercentatgeJugadors.put(user.getUsername(), user.getPercentatge());
				}
			}
		}
		return llistatPercentatgeJugadors;
	}

	//Calcula la mitja del jugadors
	public double mitjaJugadors(){
		List<User> llistatUsers = userRepository.findAll();
		double mitja = llistatUsers.stream().mapToDouble(User::calculaPercentatgeExitJugador).average().orElse(0.0);
		return Math.round(mitja *100.0)/100.0;
	}

	//Llista de tots els jugadors amb el seu percentatge, per poder buscar millor i pitjor jugador
	public List<User> getListJugadorsRanking (List<User> llistaUsers){
		List<User> users = new ArrayList<>();
		double percentatge;

		for(User user: llistaUsers){
			percentatge = user.calculaPercentatgeExitJugador();
			user.setPercentatge(percentatge);
			users.add(user);
			userRepository.save(user);
		}
		return users;
	}

	//Retorna el pitjor jugador
	public UserDto jugadorLoser() {
		List<User> llistatUsers = userRepository.findAll();
		List<User> users = getListJugadorsRanking(llistatUsers);
		users.sort(Comparator.comparing(User::calculaPercentatgeExitJugador));
		return convertEntitatADto(users.get(0));
	}

	//Retorna el millor jugador
	public UserDto jugadorBest() {
		List<User> llistatUsers = userRepository.findAll();
		List<User> users = getListJugadorsRanking(llistatUsers);
		users.sort(Comparator.comparing(User::calculaPercentatgeExitJugador));
		return convertEntitatADto (users.get(users.size() - 1));
	}

	//MODELMAPPERS

	//Convertim DTO a entitat utilitzan el ModelMapper
	public User convertDTOAEntitat (UserDto userDto){
		return modelMapper.map(userDto, User.class);
	}

	//Convertim entitat a DTO utilitzan el ModelMapper
	public UserDto convertEntitatADto (User user){
		return modelMapper.map(user, UserDto.class);
	}
}

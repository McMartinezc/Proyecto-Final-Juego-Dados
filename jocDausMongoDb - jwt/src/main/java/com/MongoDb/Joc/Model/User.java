package com.MongoDb.Joc.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Getter
@Setter
@AllArgsConstructor


@Document(collection = "jugador")
public class User {

	@Id
	private String id;
	@Field(name = "username")
	private String username;

	//jwt
	private String password;
	@Field(name = "data_registre")
	@DateTimeFormat(iso = ISO.DATE)
	private Date dataRegistre;

	@DBRef(lazy = true)
	private List<Tirada> misTiradas;

	@JsonIgnore
	@Transient
	private double percentatge;

	public User(String id, String username, Date dataRegistre) {
		this.id = id;
		this.username = username;
		this.dataRegistre = dataRegistre;
		this.misTiradas = misTiradas;
	}
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	public User() {
		this.username = username;
	}

	//calcula el percentatge d'èxit del jugador

	public double calculaPercentatgeExitJugador(){
		int totalGuanyat =0;
		int tamanyLlista = misTiradas.size();

		if (misTiradas != null && tamanyLlista > 0){
			for (Tirada tirada: misTiradas){
				if (tirada.isGuanya()){
					totalGuanyat ++;
				}
			}
			percentatge = (totalGuanyat *100) / tamanyLlista;
		}
		return percentatge;
	}

	//Guardem la tirada del jugador a la llista de les seves tirades
	public void addTirada (Tirada miTirada){

		if (misTiradas ==null){
			misTiradas = new ArrayList<Tirada>();
		}
		misTiradas.add(miTirada);
	}


}

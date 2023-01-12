package com.MongoDb.Joc.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Getter
@Setter

@Document(collection = "tirades")
public class Tirada {


	@Id
	private String id;
	@Field(name = "dau1")
	private int dau1;
	@Field(name = "dau2")
	private int dau2;
	@Field(name = "guanyada")
	private boolean guanya;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "idUser")
	private String idUser;

	public Tirada() {
	}
	
	public Tirada(String idUser) {
		this.dau1 = generarTirada();
		this.dau2 = generarTirada();
		this.guanya = resultatTirada();
		this.idUser = idUser;
	}

	public Tirada(int dau1, int dau2, boolean guanya, String idUser) {
		this.dau1 = dau1;
		this.dau2 = dau2;
		this.guanya = guanya;
		this.idUser = idUser;
	}

	public String getUser_id() {
		return idUser;
	}
	public void setUser_id(String user_id) {
		this.idUser = user_id;
	}

	//Metode que comprova el resultat sigui 7 guanya, dona true si ha guanyat o false si ha perdut
	public boolean resultatTirada(){
		boolean resultat;

		if((this.dau1 + this.dau2) == 7){
			resultat = true;
		}else{
			resultat = false;
		}
		return resultat;
	}

	//Valor random dels daus
	public int generarTirada(){
		int random = (int) Math.floor(Math.random()* 6 + 1); //Metode que genera un número random del 1 al 6
		return random;
	}
}

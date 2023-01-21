package com.MongoDb.Joc.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter

@Document(collection = "tirades")
public class Tirada {


    @Id
    private int id;

    //variable para canviar el id generat per mongo por un número autoincremental
    @Transient
    public static final String SEQUENCE_NAME = "tirades_sequence";

    @Field(name = "dau1")
    private int dau1;

    @Field(name = "dau2")
    private int dau2;

    @Field(name = "guanyada")
    private boolean guanya;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "idjugador")
    private int idjugador;

    public Tirada() {
    }

    public Tirada(int idjugador) {
        this.dau1 = generarTirada();
        this.dau2 = generarTirada();
        this.guanya = resultatTirada();
        this.idjugador = idjugador;
    }

    public Tirada(int dau1, int dau2, boolean guanya, int idjugador) {
        this.dau1 = dau1;
        this.dau2 = dau2;
        this.guanya = guanya;
        this.idjugador = idjugador;
    }


    public int getJugador_id() {
        return idjugador;
    }

    public void setJugador_id(int jugador_id) {
        this.idjugador = jugador_id;
    }

    //Metode que comprova el resultat sigui 7 guanya, dona true si ha guanyat o false si ha perdut
    public boolean resultatTirada() {
        boolean resultat;

        if ((this.dau1 + this.dau2) == 7) {
            resultat = true;
        } else {
            resultat = false;
        }
        return resultat;
    }

    //Valor random dels daus
    public int generarTirada() {
        int random = (int) Math.floor(Math.random() * 6 + 1); //Metode que genera un número random del 1 al 6
        return random;
    }
}

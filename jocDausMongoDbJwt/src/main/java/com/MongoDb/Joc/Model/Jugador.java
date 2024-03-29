package com.MongoDb.Joc.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.MongoDb.Joc.Exception.AlreadyExist;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
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
@NoArgsConstructor

@Document(collection = "jugador")
public class Jugador {

    @Id
    private int id;
    //variable per canviar el id generat per mongo per un número autoincremental
    @Transient
    public static final String SEQUENCE_NAME = "jugador_sequence";
    @Field(name = "username")
    private String username;
    //jwt
    private String password;
    @Field(name = "data_registre")
    @CreatedDate
    private Date dataRegistre;
    @DBRef(lazy = true)
    private List<Tirada> misTiradas;
    @JsonIgnore
    @Transient
    private double percentatge;

    public Jugador(String username) {
        this.username = username;
    }

    public Jugador(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Jugador(int id, String username, Date dataRegistre, List<Tirada> misTiradas, double percentatge) {
        this.id = id;
        this.username = username;
        this.dataRegistre = dataRegistre;
        this.misTiradas = misTiradas;
        this.percentatge = percentatge;
    }

    //calcula el percentatge d'èxit del jugador

    public double calculaPercentatgeExitJugador() {
        int totalGuanyat = 0;
        int tamanyLlista = misTiradas.size();

        if (misTiradas != null && tamanyLlista > 0) {
            for (Tirada tirada : misTiradas) {
                if (tirada.isGuanya()) {
                    totalGuanyat++;
                }
            }
            percentatge = (totalGuanyat * 100) / tamanyLlista;
        }
        return percentatge;
    }

    //Guardem la tirada del jugador a la llista de les seves tirades
    public void addTirada(Tirada miTirada) {

        if (misTiradas == null) {
            misTiradas = new ArrayList<Tirada>();
        }
        misTiradas.add(miTirada);
    }


}

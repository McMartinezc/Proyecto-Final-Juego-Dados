package com.MongoDb.Joc.Dto;

import com.MongoDb.Joc.Model.Tirada;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.util.Date;
import java.util.List;

@Setter
@Getter
@ToString

public class JugadorDto {

    private int id;
    //variable per canviar el id generat per mongo per un número autoincremental
    public static final String SEQUENCE_NAME = "jugador_sequence";

    private String nom;

    private Date dataRegistre;

    private List<Tirada> misTiradas;

    private double percentatge;


}

package com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@ToString
@Data

@Entity
@Table(name="jugador")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id_jugador")
    private Long id;

    @Column(name ="nom_jugador")
    private String username;
    //jwt
    private String password;
    @Column(name="data_registre")

    //Anotación que crea la fecha
    @CreationTimestamp
    private LocalDate dataRegistre;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tirada> misTiradas;

    private double percentatge;

    //Constructors
    public User(){
        this.username=username;
    }
    public User(Long id, String username, LocalDate dataRegistre) {
        this.id = id;
        this.username = username;
        this.dataRegistre = dataRegistre;
        misTiradas = new ArrayList<Tirada>();
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //Càlcul de percentatge d'èxit del jugador
    public double calculaPercentatgeExitJugador(){
        int totalGuanyat =0;
        int tamanyLlista = misTiradas.size();

        //Comprovem que la llista no està buida
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

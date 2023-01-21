package com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Model;

import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.entities.Role;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@ToString
@Data

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_Id")
    private Long id;

    @Column(name = "nom_jugador")
    private String username;
    //jwt
    private String password;
    @Column
    private String email;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLES",
            joinColumns = {
                    @JoinColumn(name = "USER_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "ROLE_ID") })

    private Set<Role> roles;

    @Column(name = "data_registre")
    //Anotación que crea la fecha
    @CreationTimestamp
    private LocalDate dataRegistre;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tirada> misTiradas;

    private double percentatge;

    //Constructors
    public User() {
        this.username = username;
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
    public double calculaPercentatgeExitJugador() {
        int totalGuanyat = 0;
        int tamanyLlista = misTiradas.size();

        //Comprovem que la llista no està buida
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

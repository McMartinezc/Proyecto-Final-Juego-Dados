package com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.Model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Setter
@Getter
@AllArgsConstructor

// Representa la tabla Rol

@Entity
@Table(name="roles")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    //Se indica que va a ser un Enum de tipo String
    @Enumerated(EnumType.STRING)
    private ERole name;

    public Rol() {
    }

    public Rol(ERole name) {
        this.name = name;
    }

}

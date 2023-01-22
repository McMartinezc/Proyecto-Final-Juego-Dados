package com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
@Getter
@Setter

//Clase que hace de DTO para el nuevo usuario.

public class NuevoUser {

    private String nombreUsuario;
    private String email;
    private String password;
    //Por defecto crea un usuario normal
    //Si quiero un usuario Admin debo pasar este campo roles
    private Set<String> roles = new HashSet<>();

}

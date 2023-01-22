package com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

//Clase que hace de DTO para el login de usuario
public class LoginUser {
    private String nombreUsuario;
    private String password;

}

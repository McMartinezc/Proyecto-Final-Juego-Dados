package com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Util;

import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.Model.Rol;
import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.Model.ERole;
import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.Service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// Comentar o borrar clase despues del primer run de la aplicación
@Component
public class CreateRoles implements CommandLineRunner {

    @Autowired
    RolService rolService;

    @Override
    public void run(String... args) throws Exception {
       Rol rolUser = new Rol(ERole.ROLE_USER);
       rolService.save(rolUser);
    }
}
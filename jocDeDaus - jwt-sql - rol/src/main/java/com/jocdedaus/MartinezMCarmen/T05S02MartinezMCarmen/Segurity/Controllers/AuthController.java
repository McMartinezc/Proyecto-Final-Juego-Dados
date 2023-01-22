package com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.Controllers;

import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.DTO.UserDto;
import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Model.User;
import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.Dto.JwtDto;
import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.Dto.LoginUser;
import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.Dto.NuevoUser;
import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.Model.Rol;
import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.Model.ERole;
import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.Service.RolService;
import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.jwt.JwtProvider;
import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

//Clase que contiente los endpoints para hacer login, crear un nuevo usuario y update.

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService usuarioService;

    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;

    //Espera un json y lo convierte a tipo clase NuevoUsuario
    @PostMapping("/register")
    public ResponseEntity<?> nuevoUsuario(@RequestBody NuevoUser nuevoUsuario,
                                          BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(("Campos mal o email invalido"), HttpStatus.BAD_REQUEST);
        }
        if(usuarioService.existsByUsuario(nuevoUsuario.getNombreUsuario())){
            return new ResponseEntity<>(("Ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        }
        if(usuarioService.existsByEmail(nuevoUsuario.getEmail())){
            return new ResponseEntity<>(("Ese email ya existe"), HttpStatus.BAD_REQUEST);
        }

        User usuario = new User(nuevoUsuario.getNombreUsuario(),
                nuevoUsuario.getEmail(), passwordEncoder.encode(nuevoUsuario.getPassword()));

        Set<Rol> roles = new HashSet<>();
        roles.add(rolService.getByRolName(ERole.ROLE_USER).get());
        usuario.setRoles(roles);
        usuarioService.save(usuario);
        return new ResponseEntity<>(("Usuario creado"), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@RequestBody LoginUser loginUsuario, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(("Campos mal"), HttpStatus.BAD_REQUEST);
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(),
                                loginUsuario.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
        return new ResponseEntity<>(jwtDto, HttpStatus.OK);
    }

    @PutMapping("/update/{username}")
    public ResponseEntity<User> updateUser(@PathVariable String username, @RequestBody UserDto userDto, @RequestHeader("Authorization") String token) {
        String jwt = token.substring(7);
        String userName = jwtProvider.getNombreUsuarioFromToken(jwt);
        User user = usuarioService.getByUsuario(userName).get();
        if(user == null || !user.getUsername().equals(username)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if(userDto.getUsername() != null)
            user.setUsername(userDto.getUsername());
        if(userDto.getEmail() != null)
            user.setEmail(userDto.getEmail());
        if(userDto.getPassword() != null)
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        usuarioService.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}


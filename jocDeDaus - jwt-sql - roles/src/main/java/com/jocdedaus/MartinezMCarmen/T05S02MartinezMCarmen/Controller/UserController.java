package com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Controller;

import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.DTO.UserDto;
import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Model.User;
import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.config.TokenProvider;
import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.dto.AuthToken;
import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.dto.LoginUser;
import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.service.UserServiceImpl;
import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Services.TiradaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.TreeMap;

@RestController
@RequestMapping("/players/")
public class UserController {
    @Autowired
    private TiradaService tiradaService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenProvider jwtTokenUtil;
    @Autowired
    private UserServiceImpl userServiceImpl;


    //METODES CRUD

    //Mostra tots els jugadors
    @GetMapping("/getAll")
    public ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity.ok(tiradaService.getAllUsers());
    }


    //Buscar un jugador
    @GetMapping("getOne/{id}")
    public ResponseEntity<UserDto> getOne(@PathVariable Long id) {
        return new ResponseEntity<>(tiradaService.getOne(id), HttpStatus.OK);
    }

    //Update jugador
    @PutMapping("/update/{id}")
    public ResponseEntity<UserDto> updateJugador(@PathVariable Long id, @RequestBody UserDto userDto) {

        return new ResponseEntity<>(tiradaService.updateUser(id, userDto), HttpStatus.CREATED);
    }

    //Borrar llistat de tirades d'un jugador
    @DeleteMapping("/deleteTirades/{id}")
    public ResponseEntity<UserDto> deleteTiradaJugador(@PathVariable Long id) {
        tiradaService.deleteTirades(id);
        return ResponseEntity.noContent().build();
    }

    //LLISTATS JUGADORS

    //GET /players/ranking: retorna el ranking de tots els jugadors/es del sistema.
    @GetMapping("/ranking")
    public ResponseEntity<TreeMap<String, Double>> getLlistatRankingMigJugadors() {
        return new ResponseEntity<>(tiradaService.llistatRankingJugadors(), HttpStatus.OK);
    }

    //GET /players/: retorna el percentatge mitjà d’èxits de tots els jugadors.
    @GetMapping("/")
    public ResponseEntity<Double> getPercentatgeMitja() {
        double percentatgeExit = tiradaService.mitjaJugadors();
        return new ResponseEntity<>(percentatgeExit, HttpStatus.OK);
    }

    //GET /players/ranking/loser: retorna el jugador/a amb pitjor percentatge d’èxit.
    @GetMapping("/ranking/loser")
    public ResponseEntity<UserDto> getJugadorLoser() {
        return new ResponseEntity<>(tiradaService.jugadorLoser(), HttpStatus.OK);
    }

    //GET /players/ranking/loser: retorna el jugador/a amb millor percentatge d’èxit.
    @GetMapping("/ranking/winner")
    public ResponseEntity<UserDto> getJugadorBest() {
        return new ResponseEntity<>(tiradaService.jugadorBest(), HttpStatus.OK);
    }

    //JWT I ROLES
    @PostMapping("/authenticate")
    public ResponseEntity<?> generateToken(@RequestBody LoginUser loginUser) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        return ResponseEntity.ok(new AuthToken(token));
    }

    @PostMapping("/register")
    public User saveUser(@RequestBody com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.dto.UserDto user){
        return userServiceImpl.save(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/hello-admin")
    public String adminPing(){
        return "Only Admins Can Read This";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/hello-admin-user")
    public String adminUser(){
        return "Only Admins and Users Can Read This";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/hello-user")
    public String userPing(){
        return "Any User Can Read This";
    }
}

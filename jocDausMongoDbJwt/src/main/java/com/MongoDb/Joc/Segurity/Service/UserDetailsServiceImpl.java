package com.MongoDb.Joc.Segurity.Service;


import com.MongoDb.Joc.Model.Jugador;
import com.MongoDb.Joc.repository.JugadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private JugadorRepository jugadorRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Jugador jugador = jugadorRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username" + username));
        return new User(jugador.getUsername(), jugador.getPassword(), new ArrayList<>());
    }

}

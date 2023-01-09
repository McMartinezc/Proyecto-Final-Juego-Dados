package com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.Controllers;

import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.DTO.UserDto;
import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Model.User;
import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Repository.UserRepository;
import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.jwt.JwtUtils;
import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.payload.JwtResponse;
import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.payload.LoginRequest;
import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.payload.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);


        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@RequestBody UserDto signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

       // if (userRepository.existsByEmail(signUpRequest.getEmail())) {
         //   return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
       // }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),encoder.encode(signUpRequest.getPassword()));

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));

    }
}

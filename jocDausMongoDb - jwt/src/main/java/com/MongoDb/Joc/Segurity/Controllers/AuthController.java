package com.MongoDb.Joc.Segurity.Controllers;



import com.MongoDb.Joc.Dto.UserDto;
import com.MongoDb.Joc.Model.User;
import com.MongoDb.Joc.Segurity.jwt.JwtUtils;
import com.MongoDb.Joc.Segurity.payload.JwtResponse;
import com.MongoDb.Joc.Segurity.payload.LoginRequest;
import com.MongoDb.Joc.Segurity.payload.MessageResponse;
import com.MongoDb.Joc.repository.UserRepository;
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
    public ResponseEntity<MessageResponse> registerUser (@RequestBody UserDto signUpRequest) {

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username ja existeix!"));
        }
        // Si tinguessim email
        // if (userRepository.existsByEmail(signUpRequest.getEmail())) {
        //   return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        // }
        //Verifiquem si el nom és null o és buit envia un missatge ja que per seguretat ha de tenir un nom d'usuari
        if (signUpRequest.getUsername() == null || "".equals(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Has d'introduïr un nom!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));

    }
}

package com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.dto;


import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserDto {
    
    private String username;
    private String password;
    private String email;


    public User getUserFromDto(){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        
        return user;
    }
    
}
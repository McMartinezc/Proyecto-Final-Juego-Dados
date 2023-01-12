package com.MongoDb.Joc.Dto;

import com.MongoDb.Joc.Model.Tirada;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


import java.util.Date;
import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor

public class UserDto {

    private String id;
    private String username;
    private String password;
    private Date dataRegistre;
    private List<Tirada> misTiradas;
    private double percentatge;

    public UserDto (String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserDto(String username, Date dataRegistre, List<Tirada> misTiradas, double percentatge) {
        this.username = username;
        this.dataRegistre = dataRegistre;
        this.misTiradas = misTiradas;
        this.percentatge = percentatge;
    }

}

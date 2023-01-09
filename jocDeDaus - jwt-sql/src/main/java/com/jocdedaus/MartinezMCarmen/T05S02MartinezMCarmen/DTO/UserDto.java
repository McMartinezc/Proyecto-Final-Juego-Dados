package com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.DTO;

import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Model.Tirada;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor

public class UserDto {
    private Long id;
    private String username;
    private String password;
    private LocalDate dataRegistre;
    private List<Tirada> misTiradas;
    private double percentatge;

    public UserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserDto(String username, LocalDate dataRegistre, List<Tirada> misTiradas, double percentatge) {
        this.username = username;
        this.dataRegistre = dataRegistre;
        this.misTiradas = misTiradas;
        this.percentatge = percentatge;
    }
}

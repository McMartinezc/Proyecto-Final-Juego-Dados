package com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.Service;

import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.Model.Rol;
import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.Model.ERole;
import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.Repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RolService {
    @Autowired
    RolRepository rolRepository;

    public Optional<Rol> getByRolName(ERole ERole){
        return  rolRepository.findByName(ERole);
    }

    public void save(Rol rol){
        rolRepository.save(rol);
    }
}

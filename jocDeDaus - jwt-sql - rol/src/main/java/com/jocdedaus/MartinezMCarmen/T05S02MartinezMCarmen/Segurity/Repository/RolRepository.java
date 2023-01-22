package com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.Repository;

import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.Model.Rol;
import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.Model.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByName(ERole name);
}

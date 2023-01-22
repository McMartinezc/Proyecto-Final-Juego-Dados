package com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.repository;

import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByName(String name);
}
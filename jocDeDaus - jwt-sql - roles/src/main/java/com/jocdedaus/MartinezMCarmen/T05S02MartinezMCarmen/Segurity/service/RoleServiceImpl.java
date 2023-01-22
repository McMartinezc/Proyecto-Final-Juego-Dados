package com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.service;

import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.entities.Role;
import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findByName(String name) {
        return roleRepository.findRoleByName(name);
    }
}

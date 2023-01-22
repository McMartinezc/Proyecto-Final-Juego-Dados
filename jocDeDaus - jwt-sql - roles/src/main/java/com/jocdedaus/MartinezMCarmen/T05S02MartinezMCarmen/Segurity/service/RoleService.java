package com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.service;

import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Segurity.entities.Role;

public interface RoleService {
    Role findByName(String name);
}

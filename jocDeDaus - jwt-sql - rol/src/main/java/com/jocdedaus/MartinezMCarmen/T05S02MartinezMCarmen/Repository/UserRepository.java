package com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Repository;

import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    boolean existsByEmail (String email);

}

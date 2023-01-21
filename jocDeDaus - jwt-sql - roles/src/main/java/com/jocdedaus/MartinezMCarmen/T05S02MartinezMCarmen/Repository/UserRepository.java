package com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Repository;

import com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByUsername(String username);
    User findByUsername(String username);
    boolean existsByEmail(String email);

}

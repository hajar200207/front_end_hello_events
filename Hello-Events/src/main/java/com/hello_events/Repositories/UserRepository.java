package com.hello_events.Repositories;

import com.hello_events.Entites.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findUserByUsername(String username);
    boolean existsByRole(User.Role role);
    Optional<User> findByEmail(String email);

}

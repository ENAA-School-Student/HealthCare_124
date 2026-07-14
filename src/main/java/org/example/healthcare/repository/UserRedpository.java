package org.example.healthcare.repository;

import org.example.healthcare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRedpository extends JpaRepository<User , Long> {
    Optional<User> findByEmail(String email);
}

package com.solodkov.homebank.repository;

import com.solodkov.homebank.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByName(String username);

  Optional<User> findByNameAndPin(String username, String pin);
}

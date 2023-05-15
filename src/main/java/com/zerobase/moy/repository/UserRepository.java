package com.zerobase.moy.repository;

import com.zerobase.moy.data.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

  User getByEmail(String email);

  Integer countByEmail(String email);
}

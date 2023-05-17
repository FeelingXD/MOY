package com.zerobase.moy.repository;

import com.zerobase.moy.data.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

  @Query("select u from User u left join fetch u.roles where u.email =:email and u.deleted is false ")
  User getByEmailAndDeletedIsFalse(String email);

  @Query("select u from User u left join fetch u.roles where u.id =:id and u.deleted is false ")
  Optional<User> findByIdAndDeletedIsFalse(Long id);

  Integer countByEmail(String email);
}

package com.example.jpa.repository;

import com.example.jpa.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserIdAndDeleteAtIsNull(Integer userId);

    List<User> findAllByDeleteAtIsNull();

    Page<User> findAllByDeleteAtIsNull(Pageable pageable);

    boolean existsByEmail(String email);
}

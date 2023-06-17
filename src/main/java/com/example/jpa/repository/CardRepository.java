package com.example.jpa.repository;

import com.example.jpa.model.Card;
import com.example.jpa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {
    Optional<Card> findByCardIdAndDeleteAtIsNull(Integer cardId);
}

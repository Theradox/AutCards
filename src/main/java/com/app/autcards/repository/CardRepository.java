package com.app.autcards.repository;

import com.app.autcards.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findById(Long id);
    Card save(Card card);
    void deleteById(Long id);
}

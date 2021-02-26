package com.app.autcards.repository;
import com.app.autcards.model.Deck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Long>{
    Optional<Deck> findById(Long id);
    Deck save(Deck deck);
    void deleteById(Long id);
}
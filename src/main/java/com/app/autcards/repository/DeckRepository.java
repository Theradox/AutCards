package com.app.autcards.repository;
import com.app.autcards.model.Deck;
import com.app.autcards.model.enumerations.DeckOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Long>{
    Optional<Deck> findById(Long id);
    Deck save(Deck deck);
    void deleteById(Long id);
    List<Deck> findAllByUserEmail(String email);
    List<Deck> findAllByOwnerIs(DeckOwner owner);
    List<Deck> findByNameContainingAndUserEmail(String name, String email);
    List<Deck> findByNameContainingAndOwnerIs(String name, DeckOwner owner);
    List<Deck> findByNameContaining(String name);
    Boolean existsByNameContainsAndDescriptionContainsAndUserEmail(String name, String description, String email);
}

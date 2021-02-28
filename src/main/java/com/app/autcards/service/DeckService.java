package com.app.autcards.service;

import com.app.autcards.model.Deck;

import java.util.List;

public interface DeckService {
    List<Deck> findAll();
    List<Deck> findAllByUser();
    Deck findById(Long id);
    Deck saveDeck (Deck deck);
    Deck updateName(Long id, String name);
    void deleteById(Long id);

}

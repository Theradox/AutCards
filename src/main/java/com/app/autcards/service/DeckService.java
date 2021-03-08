package com.app.autcards.service;

import com.app.autcards.model.Deck;

import java.util.List;

public interface DeckService {
    List<Deck> findAll();
    List<Deck> findAllByUser();
    Deck findById(Long id);
    Deck saveDeck (Deck deck);
    Deck saveToPublicDeck (Long id);
    Deck saveToPrivateDeck(Long id);
    Deck saveToDecks(Long id);
    Deck updateDeck(Long id, Deck deck);
    void deleteById(Long id);

}

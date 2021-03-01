package com.app.autcards.service;

import com.app.autcards.model.Card;

import java.util.List;

public interface CardService {
    List<Card> findAll();

    List<Card> findAllByDeckId(Long deckId);

    Card findById(Long id);

    Card saveCard(String question, String answer);

    Card save(Card card, Long deckId);

    Card updateCard(Long id, Card card);

    void deleteById(Long id);
}

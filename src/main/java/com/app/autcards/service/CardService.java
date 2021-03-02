package com.app.autcards.service;

import com.app.autcards.model.Card;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;

public interface CardService {
    List<Card> findAll();

    List<Card> findAllByDeckId(Long deckId);

    Card findById(Long id);

    Card saveCard(Card card);

    Card save(Card card, Long deckId);

    Card updateCard(Long id, Card card);

    void deleteById(Long id, Long deckId);

    List<Card> findByPostponed(List<Card> cards);

    public Card updateCardOneDay(Long id, Card card);
    public Card updateCardTwoDay(Long id, Card card);
    public Card updateCardFiveDay(Long id, Card card);

    public void clearAll(Long deckId);
}

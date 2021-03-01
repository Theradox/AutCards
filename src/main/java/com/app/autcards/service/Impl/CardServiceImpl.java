package com.app.autcards.service.Impl;

import com.app.autcards.model.Card;
import com.app.autcards.model.Deck;
import com.app.autcards.repository.CardRepository;
import com.app.autcards.repository.DeckRepository;
import com.app.autcards.service.CardService;
import com.app.autcards.service.DeckService;
import lombok.AllArgsConstructor;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final DeckService deckService;
    private final DeckRepository deckRepository;


    @Override
    public List<Card> findAll() {
        return this.cardRepository.findAll();
    }

    @Override
    public List<Card> findAllByDeckId(Long deckId) {
        return null;
    }

    @Override
    public Card findById(Long id) {
        return this.cardRepository.findById(id).orElseThrow(() -> new ExpressionException("Card not found"));
    }

    @Override
    public Card saveCard(String question, String answer) {
        var card = new Card(question, answer);
        return this.cardRepository.save(card);
    }

    @Override
    public Card save(Card card, Long deckId) {
        var deck = this.deckService.findById(deckId);
        var cards = deck.getCards();
        cards.add(card);
        deck.setCards(cards);
        cardRepository.save(card);
        deckRepository.save(deck);
        return card;
    }

    @Override
    public Card updateCard(Long id, Card card) {
        var card1 = this.findById(id);
        card1.setAnswer(card.getAnswer());
        card1.setQuestion(card.getQuestion());
        return this.cardRepository.save(card1);
    }

    @Override
    public void deleteById(Long id) {
        this.cardRepository.deleteById(id);
    }
}

package com.app.autcards.service.Impl;

import com.app.autcards.model.Deck;
import com.app.autcards.repository.DeckRepository;
import com.app.autcards.service.DeckService;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeckServiceImpl implements DeckService {
    private final DeckRepository deckRepository;

    public DeckServiceImpl(DeckRepository deckRepository) {
        this.deckRepository = deckRepository;
    }


    @Override
    public List<Deck> findAll() {
        return this.deckRepository.findAll();
    }

    @Override
    public Deck findById(Long id) {
        return this.deckRepository.findById(id).orElseThrow(() -> new ExpressionException("Deck not found"));
    }

    @Override
    public Deck save(Deck deck) {
        return this.deckRepository.save(deck);
    }

    @Override
    public Deck updateName(Long id, String name) {
        Deck deck = this.findById(id);
        deck.setName(name);
        return this.deckRepository.save(deck);
    }

    @Override
    public void deleteById(Long id) {
        this.deckRepository.deleteById(id);
    }
}

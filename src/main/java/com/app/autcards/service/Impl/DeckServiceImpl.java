package com.app.autcards.service.Impl;

import com.app.autcards.model.Deck;
import com.app.autcards.model.OauthUser;
import com.app.autcards.model.user_details.MyAuthenticatedPrincipal;
import com.app.autcards.repository.DeckRepository;
import com.app.autcards.service.DeckService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import static com.app.autcards.model.enumerations.DeckOwner.*;

@Service
@AllArgsConstructor
public class DeckServiceImpl implements DeckService {
    private final DeckRepository deckRepository;
    private final MyUserService myUserService;


    @Override
    public List<Deck> findAll() {
        return this.deckRepository.findAllByOwnerIs(PUBLIC);
    }

    @Override
    public List<Deck> findAllByUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyAuthenticatedPrincipal principal = (MyAuthenticatedPrincipal) auth.getPrincipal();
        return this.deckRepository.findAllByUserEmail(principal.getEmail());
    }


    @Override
    public Deck findById(Long id) {
        return this.deckRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Deck not found"));
    }

    @Override
    public Deck saveDeck(Deck deck) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyAuthenticatedPrincipal principal = (MyAuthenticatedPrincipal) auth.getPrincipal();

        OauthUser user = this.myUserService.findById(principal.getEmail());
        deck.setUser(user);
        deck.setOwner(PRIVATE);
        return this.deckRepository.save(deck);
    }

    @Override
    public Deck saveToPublicDeck(Long id) {
        var deck = deckRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Deck not found"));
        deck.setOwner(PUBLIC);
        return this.deckRepository.save(deck);
    }

    @Override
    public Deck saveToPrivateDeck(Long id) {
        var deck = deckRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Deck not found"));
        deck.setOwner(PRIVATE);
        return this.deckRepository.save(deck);
    }

    @Override
    public Deck saveToDecks(Long id) {
        var deck = deckRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Deck not found"));
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var principal = (MyAuthenticatedPrincipal) auth.getPrincipal();
        var user = this.myUserService.findById(principal.getEmail());

        var deck1 = new Deck();
        deck1.setOwner(PRIVATE);
        deck1.setName(deck.getName());
        deck1.setDescription(deck.getDescription());
        deck1.setUser(user);
        return this.deckRepository.save(deck1);
    }


    @Override
    public Deck updateDeck(Long id, Deck deck) {
        Deck deck1 = this.findById(id);
        deck1.setName(deck.getName());
        deck1.setDescription(deck.getDescription());
        deck1.setCards(deck.getCards());
        return this.deckRepository.save(deck1);
    }

    @Override
    public void deleteById(Long id) {
        this.deckRepository.deleteById(id);
    }
}

package com.app.autcards.presentation;

import com.app.autcards.model.Card;
import com.app.autcards.model.Deck;
import com.app.autcards.service.DeckService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/decks")
public class DeckController {
    private final DeckService deckService;


    public DeckController(DeckService deckService) {
        this.deckService = deckService;
    }

    @GetMapping
    public String getDeckPage(Model model) {
        List<Deck> decks = this.deckService.findAll();
        model.addAttribute("decks", decks);
        return "decks";
    }

    @GetMapping("/{id}/open")
    public String openCards(Model model, @PathVariable Long id) {
        try {
            Deck deck = this.deckService.findById(id);
            List<Card> cards = deck.getCards();

            model.addAttribute("cards", cards);
            model.addAttribute("deck", deck);

            return "cards";
        } catch (RuntimeException ex) {
            return "redirect:/decks?error=" + ex.getMessage();
        }

    }

    @GetMapping("/add_deck")
    public String addNewDeck(Model model) {
        model.addAttribute("deck", new Deck());
        return "add_deck";
    }

    @PostMapping("/save")
    public String saveDeck(@Valid Deck deck) {
        this.deckService.save(deck);
        return "redirect:/decks";
    }
}

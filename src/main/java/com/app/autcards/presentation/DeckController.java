package com.app.autcards.presentation;

import com.app.autcards.model.Card;
import com.app.autcards.model.Deck;
import com.app.autcards.service.CardService;
import com.app.autcards.service.DeckService;
import com.app.autcards.service.Impl.MyUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/decks")
@AllArgsConstructor
public class DeckController {
    private final DeckService deckService;
    private final CardService cardService;
    private final MyUserService myUserService;

    @GetMapping
    public String getDeckPage(Model model) {
        List<Deck> decks = this.deckService.findAllByUser();
        model.addAttribute("decks", decks);
        return "decks";
    }

    @GetMapping(value = "/{id}/open")
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

    @GetMapping(value = "/add_deck")
    public String addNewDeck(Model model) {
        model.addAttribute("deck", new Deck());
        return "add_deck";
    }

    @PostMapping(value = "/save")
    public String saveDeck(@Valid Deck deck) {
        this.deckService.saveDeck(deck);
        return "redirect:/decks";
    }
    @PostMapping(value = "/{id}/delete")
    public String deleteDeck(@PathVariable Long id) {
        this.deckService.deleteById(id);
        return "redirect:/decks";
    }

    @GetMapping(value = "/{id}/add_cards")
    public String addNewCard(Model model, @PathVariable Long id) {
        Deck deck = this.deckService.findById(id);
        model.addAttribute("deck", deck);
        model.addAttribute("card", new Card());
        return "add_card";
    }

    @PostMapping(value = "/{deckId}/saveCard")
    public String saveCard(@Valid Card card, @PathVariable Long deckId) {
        this.cardService.save(card, deckId);
        return "redirect:/decks/add_card";
    }


}

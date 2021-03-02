package com.app.autcards.presentation;

import com.app.autcards.model.Card;
import com.app.autcards.model.Deck;
import com.app.autcards.service.CardService;
import com.app.autcards.service.DeckService;
import com.app.autcards.service.Impl.MyUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
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
            var cards2 = cardService.findByPostponed(cards);
            model.addAttribute("cards", cards2);
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
        return "redirect:/decks/{deckId}/add_cards";
    }

    @GetMapping(value = "/{id}/update-card")
    public String updateCard(Model model, @PathVariable Long id) {
        var card = cardService.findById(id);
        model.addAttribute("card", card);
        return "card_edit";
    }

    @PostMapping(value = "/edit/{id}")
    public String updateCard(@PathVariable Long id, Card card) {
        cardService.updateCard(id, card);
        return "redirect:/decks";
    }

    @PostMapping(value = "/one-day/{id}/{deckId}")
    public String postponeDate(@PathVariable Long id, @PathVariable Long deckId) {
        var card = cardService.findById(id);
        this.cardService.updateCardOneDay(id, card);
        return "redirect:/decks/{deckId}/open";
    }


    @PostMapping(value = "/two-day/{id}/{deckId}")
    public String postponeTwoDate(@PathVariable Long id, @PathVariable Long deckId) {
        var card = cardService.findById(id);
        this.cardService.updateCardTwoDay(id, card);
        return "redirect:/decks/{deckId}/open";
    }


    @PostMapping(value = "/five-day/{id}/{deckId}")
    public String postponeFiveDate(@PathVariable Long id, @PathVariable Long deckId) {
        var card = cardService.findById(id);
        this.cardService.updateCardFiveDay(id, card);
        return "redirect:/decks/{deckId}/open";
    }


    @PostMapping(value = "/clear-all/{deckId}")
    public String clearAll(@PathVariable Long deckId) {
        this.cardService.clearAll(deckId);
        return "redirect:/decks/{deckId}/open";
    }

    @PostMapping(value = "/{id}/{deckId}/deleteCard")
    public String deleteCard(@PathVariable Long id, @PathVariable Long deckId) {
        this.cardService.deleteById(id, deckId);
        return "redirect:/decks/{deckId}/open";
    }


}

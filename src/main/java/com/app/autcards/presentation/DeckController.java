package com.app.autcards.presentation;

import com.app.autcards.model.Card;
import com.app.autcards.model.Deck;
import com.app.autcards.service.CardService;
import com.app.autcards.service.DeckService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/decks")
public class DeckController {
    private final DeckService deckService;
    private final CardService cardService;


    public DeckController(DeckService deckService, CardService cardService) {
        this.deckService = deckService;
        this.cardService = cardService;
    }
    @GetMapping
    public String getDeckPage(Model model) {
        List<Deck> decks = this.deckService.findAll();
        model.addAttribute("decks", decks);
        return "decks";
    }

    @GetMapping("/{id}/open")
    public String openCards(Model model, @PathVariable Long id){
        try{
            Deck deck = this.deckService.findById(id);
            List<Card> cards = deck.getCards();

            model.addAttribute("cards", cards);
            model.addAttribute("deck", deck);

            return "cards";
        } catch (RuntimeException ex) {
            return "redirect:/decks?error=" + ex.getMessage();
        }

    }
}

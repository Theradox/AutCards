package com.app.autcards.presentation;

import com.app.autcards.model.Card;
import com.app.autcards.model.Deck;
import com.app.autcards.model.OauthUser;
import com.app.autcards.service.CardService;
import com.app.autcards.service.DeckService;
import com.app.autcards.service.Impl.MyUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
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
        String name = "";
        model.addAttribute("name", name);
        model.addAttribute("decks", decks);
        return "decks";
    }

    @GetMapping(value = "/public-decks")
    public String publicDecks(Model model) {
        List<Deck> decks = this.deckService.findAll();
        model.addAttribute("decks", decks);
        return "public-decks";
    }

    @PostMapping(value = "/search")
    public String searchDecks(@Valid String name, Model model) {
        List<Deck> decks = this.deckService.findByNameContaining(name);
        model.addAttribute("decks", decks);
        return "decks";
    }

    @PostMapping(value = "/searchPublic")
    public String searchPublicDecks(@Valid String name, Model model) {
        List<Deck> decks = this.deckService.findByNameContainingPublic(name);
        model.addAttribute("decks", decks);
        return "public-decks";
    }

    @PostMapping(value = "/save-to-public/{id}")
    public String saveToPublicDeck(@PathVariable Long id) {
        this.deckService.saveToPublicDeck(id);
        return "redirect:/decks";
    }

    @PostMapping(value = "/clearSearch")
    public String clearSearch() {
        return "redirect:/decks";
    }

    @PostMapping(value = "/clearPublicSearch")
    public String clearPublicSearch() {
        return "redirect:/decks/public-decks";
    }

    @PostMapping(value = "/public-decks/save-to-private/{id}")
    public String saveToPrivateDeck(@PathVariable Long id) {
        this.deckService.saveToPrivateDeck(id);
        return "redirect:/decks";
    }

    //    public-decks/save-to-decks/{id}
    @PostMapping(value = "/public-decks/save-to-decks/{id}")
    public String saveToDecks(@PathVariable Long id) {
        this.deckService.saveToDecks(id);
        return "redirect:/decks";
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

//    @GetMapping(value = "/{id}/editDeck")
//    public String editDeckPage(Model model, @PathVariable Long id) {
//        try {
//            var deck = this.deckService.findById(id);
//            model.addAttribute("deck", deck);
//            return "deck_edit";
//        } catch (RuntimeException ex) {
//            return "redirect:/decks?error=" + ex.getMessage();
//        }
//    }

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
    public String saveCard(@Valid Card card,
                           @PathVariable Long deckId,
                           BindingResult bindingResult,
                           @RequestParam MultipartFile image) {
        if (bindingResult.hasErrors()) {

            return "redirect:/decks/{deckid}/open";
        }
        try {
            this.cardService.save(card, deckId, image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/decks/{deckId}/add_cards";
    }

    @GetMapping(value = "/{id}/update-deck")
    public String updateDeck(Model model, @PathVariable Long id) {
        var deck = deckService.findById(id);
        model.addAttribute("deck", deck);
        return "deck_edit";
    }

    @PostMapping(value = "/editDeck/{id}")
    public String updateDeck(@PathVariable Long id, Deck deck) {
        deckService.updateDeck(id, deck);
        return "redirect:/decks";
    }

    @GetMapping(value = "/{id}/update-card")
    public String updateCard(Model model, @PathVariable Long id) {
        var card = cardService.findById(id);
        model.addAttribute("card", card);
        return "card_edit";
    }

    @PostMapping(value = "/edit/{id}")
    public String updateCard(@PathVariable Long id, Card card, BindingResult bindingResult,
                             @RequestParam MultipartFile image) throws IOException {
        cardService.updateCard(card, id, image);
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

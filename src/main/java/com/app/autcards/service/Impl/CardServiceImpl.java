package com.app.autcards.service.Impl;

import com.app.autcards.model.Card;
import com.app.autcards.model.Deck;
import com.app.autcards.repository.CardRepository;
import com.app.autcards.repository.DeckRepository;
import com.app.autcards.service.CardService;
import com.app.autcards.service.DeckService;
import lombok.AllArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

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
    public Card saveCard(Card card) {
        return this.cardRepository.save(card);
    }

    @Override
    public Card save(Card card, Long deckId, MultipartFile image) throws IOException {
        if (image != null && !image.getName().isEmpty()) {
            byte[] bytes = image.getBytes();
            String base64Image = String.format("data:%s;base64,%s", image.getContentType(), Base64.getEncoder().encodeToString(bytes));
            card.setImageBase64(base64Image);
        }
        var deck = this.deckService.findById(deckId);
        var cards = deck.getCards();
        cards.add(card);
        deck.setCards(cards);
        card.setPostponeDate(LocalDateTime.now());
        cardRepository.save(card);
        deckRepository.save(deck);
        return card;
    }

    @Override
    public Card updateCard(Card card, Long id, MultipartFile image) throws IOException  {
        var card1 = this.findById(id);
        if (image != null && !image.getName().isEmpty()) {
            byte[] bytes = image.getBytes();
            String base64Image = String.format("data:%s;base64,%s", image.getContentType(), Base64.getEncoder().encodeToString(bytes));
            card1.setImageBase64(base64Image);
        }
        card1.setId(card.getId());
        card1.setAnswer(card.getAnswer());
        card1.setQuestion(card.getQuestion());
//        card1.setImageBase64(card.getImageBase64());
        card1.setPostponeDate(LocalDateTime.now());
        return cardRepository.save(card1);
    }
    @Override
    public void clearAll(Long deckId){
        var deck = deckService.findById(deckId);
        var cards = deck.getCards();
        cards.forEach(card -> card.setPostponeDate(LocalDateTime.now()));
        cardRepository.saveAll(cards);
    }


    @Override
    public Card updateCardOneDay(Long id, Card card) {
        var card1 = this.findById(id);
        card1.setAnswer(card.getAnswer());
        card1.setQuestion(card.getQuestion());
        card1.setPostponeDate(LocalDateTime.now().plusMinutes(10));

        return this.cardRepository.save(card1);
    }

    @Override
    public Card updateCardTwoDay(Long id, Card card) {
        var card1 = this.findById(id);
        card1.setAnswer(card.getAnswer());
        card1.setQuestion(card.getQuestion());
        card1.setPostponeDate(LocalDateTime.now().plusHours(1));

        return this.cardRepository.save(card1);
    }

    @Override
    public Card updateCardFiveDay(Long id, Card card) {
        var card1 = this.findById(id);
        card1.setAnswer(card.getAnswer());
        card1.setQuestion(card.getQuestion());
        card1.setPostponeDate(LocalDateTime.now().plusDays(1));

        return this.cardRepository.save(card1);
    }

    @Override
    public void deleteById(Long id, Long deckId) {
        var deck = this.deckService.findById(deckId);
        deck.setCards(
                deck.getCards()
                        .stream()
                        .filter(card -> !card.getId().equals(id))
                        .collect(Collectors.toList())
        );
        this.deckRepository.save(deck);
        this.cardRepository.deleteById(id);
    }

    @Override
    public List<Card> findByPostponed(List<Card> cards) {
        var cards2 = cards.stream()
                .filter(card -> !card.getPostponeDate().isAfter(LocalDateTime.now()) || card.getPostponeDate().isEqual(LocalDateTime.now())
                        || card.getPostponeDate() == null)
                .collect(Collectors.toList());
        System.out.println(cards2);
        return cardRepository.saveAll(cards2);
    }

}

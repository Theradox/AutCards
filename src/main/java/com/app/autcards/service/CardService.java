package com.app.autcards.service;

import com.app.autcards.model.Card;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface CardService {
    Card findById(Long id);


    Card save(Card card, Long deckId, MultipartFile image) throws IOException;
    Card updateCard(Card card, Long deckId, MultipartFile image) throws IOException;

    void deleteById(Long id, Long deckId);

    List<Card> findByPostponed(List<Card> cards);

    Card updateCardOneDay(Long id, Card card);
    Card updateCardTwoDay(Long id, Card card);
    Card updateCardFiveDay(Long id, Card card);

    void clearAll(Long deckId);
}

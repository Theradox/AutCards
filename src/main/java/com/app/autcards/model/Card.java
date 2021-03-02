package com.app.autcards.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String question;

    @NotNull
    private String answer;

    @NotNull
    @Lob
    private String imageBase64;

    private LocalDateTime postponeDate;

    public Card(@NotNull String question, @NotNull String answer) {
        this.question = question;
        this.answer = answer;
    }

}

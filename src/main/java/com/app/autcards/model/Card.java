package com.app.autcards.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @Column(length = 1000)
    @Size(min = 3, max = 1000)
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

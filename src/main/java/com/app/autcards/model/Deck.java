package com.app.autcards.model;

import com.app.autcards.model.enumerations.DeckOwner;
import com.app.autcards.model.enumerations.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @OneToMany
    private List<Card> cards;

    @ManyToOne
    private OauthUser user;

    @Enumerated(EnumType.STRING)
    private DeckOwner owner;

}

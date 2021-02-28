package com.app.autcards.model;

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

    @OneToMany
    private List<Card> cards;

    @ManyToOne
    private OauthUser user;

}


//TODO GET THE CURRENT LOGGED IN USER AND SHOW INFO ABOUT HIM
// TODO UPDATE LOGIN PAGE AND PERMISSIONS
//
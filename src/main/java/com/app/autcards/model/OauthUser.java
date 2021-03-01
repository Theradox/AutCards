package com.app.autcards.model;

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
@Table(name = "users")
public class OauthUser {

    @Id
    private String email;

    private String first_name;

    @Enumerated(EnumType.STRING)
    private RoleType role;
}

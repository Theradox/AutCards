package com.app.autcards.model.user_details;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface MyAuthenticatedPrincipal {

    String getUsername();
    String getEmail();
    Collection<? extends GrantedAuthority> getAuthorities();
}

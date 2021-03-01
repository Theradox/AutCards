package com.app.autcards.model.user_details;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Collection;


public class MyUser extends DefaultOidcUser implements MyAuthenticatedPrincipal {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return super.getAuthorities();
    }

    public MyUser(OidcUser user) {
        super(user.getAuthorities(), user.getIdToken());
    }

    @Override
    public String getEmail() {
        return this.getAttributes().get("email").toString();

    }

    @Override
    public String getUsername() {
        return this.getAttributes().get("given_name").toString();
    }

}

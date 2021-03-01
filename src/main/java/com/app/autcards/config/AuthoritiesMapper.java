package com.app.autcards.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class AuthoritiesMapper implements GrantedAuthoritiesMapper {
    @Override
    public Collection<? extends GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
        collection.forEach( authority -> {
            if(OidcUserAuthority.class.isInstance(authority)){
                OidcUserAuthority oidcUserAuthority = (OidcUserAuthority) authority;
                OidcIdToken idToken = oidcUserAuthority.getIdToken();
                OidcUserInfo userInfo = oidcUserAuthority.getUserInfo();

                Map<String, Object> userAttributes = oidcUserAuthority.getAttributes();

            }
            // for facebook
            else if (OAuth2UserAuthority.class.isInstance(authority)){
                OAuth2UserAuthority oAuth2UserAuthority = (OAuth2UserAuthority) authority;
                Map<String, Object> userAttributes = oAuth2UserAuthority.getAttributes();

            }
            mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        });
        return mappedAuthorities;
    }
}

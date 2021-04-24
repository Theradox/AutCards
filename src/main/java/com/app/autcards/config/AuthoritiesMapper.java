package com.app.autcards.config;

import com.app.autcards.model.OauthUser;
import com.app.autcards.model.enumerations.RoleType;
import com.app.autcards.service.Impl.MyUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
@AllArgsConstructor
public class AuthoritiesMapper implements GrantedAuthoritiesMapper {
    private final MyUserService myUserService;

    @Override
    public Collection<? extends GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
        collection.forEach( authority -> {
            if(authority instanceof OidcUserAuthority){
                OidcUserAuthority oidcUserAuthority = (OidcUserAuthority) authority;

                Map<String, Object> userAttributes = oidcUserAuthority.getAttributes();
                OauthUser user = myUserService.findById(userAttributes.get("email").toString());
                if (user == null) {
                    mappedAuthorities.add(new SimpleGrantedAuthority(RoleType.ROLE_USER.toString()));
                } else {
                    mappedAuthorities.add(new SimpleGrantedAuthority(user.getRole().toString()));

                }

            }
        });
        return mappedAuthorities;
    }
}

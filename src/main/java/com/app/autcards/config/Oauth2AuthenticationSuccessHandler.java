package com.app.autcards.config;

import com.app.autcards.model.OauthUser;
import com.app.autcards.model.enumerations.RoleType;
import com.app.autcards.model.user_details.MyAuthenticatedPrincipal;
import com.app.autcards.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


@Configuration
@AllArgsConstructor
public class Oauth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final UserRepository userRepository;
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        Map<String, Object> attributes = token.getPrincipal().getAttributes();

        DefaultOidcUser oidcUser = (DefaultOidcUser) token.getPrincipal();

        MyAuthenticatedPrincipal principal = (MyAuthenticatedPrincipal) authentication.getPrincipal();
        RoleType role = RoleType.valueOf((authentication.getAuthorities().toArray()[0]).toString());

        OauthUser user = new OauthUser(principal.getEmail(), principal.getUsername(), role);

        userRepository.save(user);
        //TODO REDIRECT PATH
        this.redirectStrategy.sendRedirect(httpServletRequest,httpServletResponse,"/");


    }


}

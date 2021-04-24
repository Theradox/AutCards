package com.app.autcards.config;

import com.app.autcards.model.OauthUser;
import com.app.autcards.model.enumerations.RoleType;
import com.app.autcards.model.user_details.MyAuthenticatedPrincipal;
import com.app.autcards.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Configuration
@AllArgsConstructor
public class Oauth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final UserRepository userRepository;
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {

        MyAuthenticatedPrincipal principal = (MyAuthenticatedPrincipal) authentication.getPrincipal();
        RoleType role = RoleType.valueOf((authentication.getAuthorities().toArray()[0]).toString());

        OauthUser user = new OauthUser(principal.getEmail(), principal.getUsername(), role);

        userRepository.save(user);
        //TODO REDIRECT PATH
        this.redirectStrategy.sendRedirect(httpServletRequest,httpServletResponse,"/");


    }


}

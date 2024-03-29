package com.app.autcards.service.Impl;

import com.app.autcards.model.OauthUser;
import com.app.autcards.model.user_details.MyUser;
import com.app.autcards.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
public class MyUserService extends OidcUserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        return new MyUser(super.loadUser(userRequest));
    }
    public OauthUser findById(String email) {
        return this.userRepository.findById(email).orElse(null);
    }
    // todo da ima 1 mesec da se vika nextShowcase(nextReminder)
    //  na sekoja od kartite da se pojavuva box, znaci kje se cuva vo baza... if now.isBefore don't showcase the question
    //  if now.isAfter, do showcase and get the same

}

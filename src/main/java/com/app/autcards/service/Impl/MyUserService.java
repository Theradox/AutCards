package com.app.autcards.service.Impl;

import com.app.autcards.model.OauthUser;
import com.app.autcards.model.user_details.MyUser;
import com.app.autcards.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class MyUserService extends OidcUserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        return new MyUser(super.loadUser(userRequest));
    }
    public OauthUser findById(String email) {
        return this.userRepository.findById(email)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User not found [%s]", email)));
    }

}

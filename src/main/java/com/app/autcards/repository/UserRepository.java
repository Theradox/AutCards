package com.app.autcards.repository;

import com.app.autcards.model.OauthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<OauthUser, String> {
}

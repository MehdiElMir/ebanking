package org.mundiapolis.digitalbankbackend.security.repositories;

import org.mundiapolis.digitalbankbackend.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppUserRepository extends JpaRepository<AppUser, String> {
    AppUser findByUsername(String username);
    AppUser findByUserId(String userId);
    List<AppUser> findByUsernameContains(String username);

}

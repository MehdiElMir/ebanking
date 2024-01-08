package org.mundiapolis.digitalbankbackend.security.repositories;

import org.mundiapolis.digitalbankbackend.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole, String> {
}

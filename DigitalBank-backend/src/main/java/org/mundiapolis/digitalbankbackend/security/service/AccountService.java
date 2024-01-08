package org.mundiapolis.digitalbankbackend.security.service;

import org.mundiapolis.digitalbankbackend.security.entities.AppRole;
import org.mundiapolis.digitalbankbackend.security.entities.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface AccountService {

    AppUser addnewUser(String username,String email, String password, String confirmPassword);
    AppRole addnewRole(String role);
    void addRoleToUser(String username, String role);
    void removeRoleFromUser(String username, String role);

    AppUser loadUserByUsername(String username);

    List<AppUser> findAllUsers();
    AppUser updateUser(AppUser appUser);
    void  deleteUser(String userID);


}

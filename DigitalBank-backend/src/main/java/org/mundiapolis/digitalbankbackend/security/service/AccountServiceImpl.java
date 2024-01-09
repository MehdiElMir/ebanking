package org.mundiapolis.digitalbankbackend.security.service;

import lombok.AllArgsConstructor;
import org.mundiapolis.digitalbankbackend.dtos.CustomerDTO;
import org.mundiapolis.digitalbankbackend.entities.Customer;
import org.mundiapolis.digitalbankbackend.security.entities.AppRole;
import org.mundiapolis.digitalbankbackend.security.entities.AppUser;
import org.mundiapolis.digitalbankbackend.security.repositories.AppRoleRepository;
import org.mundiapolis.digitalbankbackend.security.repositories.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService{
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public AppUser addnewUser(String username,String email, String password, String confirmPassword) {
        AppUser appUser =appUserRepository.findByUsername(username);
        if(appUser!=null) throw new RuntimeException("this user already exist");
        if (!password.equals(confirmPassword)) throw new RuntimeException("Password does not match!");
        appUser = AppUser.builder()
                .userId(UUID.randomUUID().toString())
                .username(username)
                .password(passwordEncoder.encode(password))
                .confirmPassword(passwordEncoder.encode(confirmPassword))
                .email(email)
                .build();
        AppUser savedAppUser = appUserRepository.save(appUser);
        return savedAppUser;
    }

    @Override
    public AppUser addUser(AppUser appUser) {
        AppUser user =appUserRepository.findByUsername(appUser.getUsername());
        if(user!=null) throw new RuntimeException("this user already exist");
        if (!appUser.getPassword().equals(appUser.getConfirmPassword())) throw new RuntimeException("Password does not match!");
         AppUser newUser = AppUser.builder()
                .userId(UUID.randomUUID().toString())
                .username(appUser.getUsername())
                .password(passwordEncoder.encode(appUser.getPassword()))
                 .confirmPassword(passwordEncoder.encode(appUser.getConfirmPassword()))
                .email(appUser.getEmail())
                .build();
        AppUser savedAppUser = appUserRepository.save(newUser);
        return savedAppUser;
    }

    @Override
    public AppRole addnewRole(String role) {
        AppRole appRole = appRoleRepository.findById(role).orElse(null);
        if(appRole!=null) throw new RuntimeException("This role already exists");
        appRole = AppRole.builder()
                .role(role)
                .build();
        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String username, String role) {
        AppUser appUser = appUserRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findById(role).get();
        appUser.getRoles().add(appRole);
        appUserRepository.save(appUser);
    }

    @Override
    public void removeRoleFromUser(String username, String role) {
        AppUser appUser = appUserRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findById(role).get();
        appUser.getRoles().remove(appRole);
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    @Override
    public List<AppUser> findAllUsers() {
        List<AppUser> appUsers= appUserRepository.findAll();
        System.out.println("***************************");
        System.out.println(appUsers);
        return appUsers;
    }

    @Override
    public AppUser updateUser(AppUser appUser) {
        AppUser user =  appUserRepository.save(appUser);
        return user;

    }

    @Override
    public void deleteUser(String userID) {
        appUserRepository.deleteById(userID);

    }

    @Override
    public AppUser findUserByID(String userID) {
        AppUser user= appUserRepository.findByUserId(userID);
        return user;
    }

    @Override
    public AppUser findUserByUsername(String username) {
        AppUser user= appUserRepository.findByUsername(username);
        return user;
    }

    @Override
    public List<AppRole> findAllRole() {
        List<AppRole> Roles= appRoleRepository.findAll();
        System.out.println("***************************");
        System.out.println(Roles);
        return Roles;

    }

    @Override
    public List<AppUser> searchUsers(String keyword) {
        List<AppUser> users = appUserRepository.findByUsernameContains(keyword);
        return users;
    }

}
package org.mundiapolis.digitalbankbackend.security;

import org.mundiapolis.digitalbankbackend.dtos.CustomerDTO;
import org.mundiapolis.digitalbankbackend.security.entities.AppRole;
import org.mundiapolis.digitalbankbackend.security.entities.AppUser;
import org.mundiapolis.digitalbankbackend.security.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class SecurityController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private AccountService accountService;
    @Autowired

    private PasswordEncoder passwordEncoder;

    @GetMapping("/profile")
    public Authentication authentication(Authentication authentication){
        return authentication;
    }


    @PostMapping("/login")
    public Map<String, String> login(String username, String password){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        Instant instant = Instant.now();
        String scope = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuedAt(instant)
                .expiresAt(instant.plus(10, ChronoUnit.MINUTES))
                .subject(username)
                .claim("scope", scope)
                .build();
        JwtEncoderParameters jwtEncoderParameters =
                JwtEncoderParameters.from(
                        JwsHeader.with(MacAlgorithm.HS512).build(),
                        jwtClaimsSet
                );
        String jwt = jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
        return Map.of("access-token",jwt);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @PostMapping("/addUser")
    public AppUser addUser(@RequestBody AppUser appUser){
        AppUser newappUser = accountService.addUser(appUser);
        return newappUser;
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @GetMapping("/users/search")
    public List<AppUser> searchUsers(@RequestParam(name = "keyword",defaultValue = "") String keyword){
        return accountService.searchUsers(keyword);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @GetMapping("/users")
    public List<AppUser> getUsers(){
        List<AppUser> users = accountService.findAllUsers();
        return users;
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @PutMapping("/update/{userID}")
    public AppUser updateUser(@PathVariable String userID ,
                              @RequestBody AppUser appUser){
        appUser.setUserId(userID);
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return accountService.updateUser(appUser);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @DeleteMapping("/delete/{userID}")
    public void deleteUser(@PathVariable String userID){
        accountService.deleteUser(userID);
    }


    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @GetMapping("/users/{userID}")
    public AppUser findUser(@PathVariable String userID){
        AppUser user = accountService.findUserByID(userID);
        return user;
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @GetMapping("/users/find")
    public AppUser findUserByName(@RequestParam (name="username",defaultValue = "")String username){
        AppUser user = accountService.findUserByUsername(username);
        return user;
    }


    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @PutMapping("/users/addRole")
    public void addRoleToUser(@RequestParam (name="username")String username,
                              @RequestParam (name="role")String role){

        accountService.addRoleToUser(username,role);

    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @PutMapping("/users/removeRole")
    public void removeRoleFromUser(@RequestParam (name="username")String username,
                                   @RequestParam (name="role")String role){

        accountService.removeRoleFromUser(username,role);

    }


    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @GetMapping("/roles")
    public List<AppRole> getRoles(){
        List<AppRole> roles = accountService.findAllRole();
        return roles;
    }





}
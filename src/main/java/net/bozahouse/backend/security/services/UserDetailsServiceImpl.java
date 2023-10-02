package net.bozahouse.backend.security.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import net.bozahouse.backend.model.entities.AppUser;
import net.bozahouse.backend.repositories.AppUserRepo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Service
@AllArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
  private AppUserRepo userRepo;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    AppUser user = userRepo.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + email));


    Collection<GrantedAuthority> authorities = new ArrayList<>();
    user.getRoles().forEach(role-> authorities.add(new SimpleGrantedAuthority(role.getName())));

    return new User(user.getEmail(), user.getPassword(), authorities);
  }






}



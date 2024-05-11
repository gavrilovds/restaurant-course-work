package ru.gavrilovds.restaurant.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.gavrilovds.restaurant.entity.Role;
import ru.gavrilovds.restaurant.entity.UserEntity;
import ru.gavrilovds.restaurant.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity user = userRepository.findByEmail(username);
    if (user == null) {
      throw new UsernameNotFoundException("User with email %s is not found".formatted(username));
    }
    Role role = user.getRole();
    if (role == null) {
      role = Role.ROLE_CUSTOMER;
    }

    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(role.toString()));
    return new User(user.getEmail(), user.getPassword(), authorities);
  }
}

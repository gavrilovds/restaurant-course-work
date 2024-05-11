package ru.gavrilovds.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gavrilovds.restaurant.entity.UserEntity;
import ru.gavrilovds.restaurant.exception.UserNotFoundException;
import ru.gavrilovds.restaurant.provider.JwtProvider;
import ru.gavrilovds.restaurant.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

  private final JwtProvider jwtProvider;
  private final UserRepository userRepository;

  public UserEntity getProfile(String jwt) {
    var email = jwtProvider.getEmailFromJwtToken(jwt);
    UserEntity user = userRepository.findByEmail(email);

    if (user == null) {
      throw new UserNotFoundException(email);
    }

    return user;
  }

  public UserEntity getUserByJwt(String jwt) {
    String email = jwtProvider.getEmailFromJwtToken(jwt);
    return userRepository.findByEmail(email);
  }
}

package ru.gavrilovds.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.gavrilovds.restaurant.controller.request.SignInRequest;
import ru.gavrilovds.restaurant.controller.request.SignUpRequest;
import ru.gavrilovds.restaurant.controller.response.AuthResponse;
import ru.gavrilovds.restaurant.entity.CartEntity;
import ru.gavrilovds.restaurant.entity.UserEntity;
import ru.gavrilovds.restaurant.exception.UserAlreadyExistsException;
import ru.gavrilovds.restaurant.provider.JwtProvider;
import ru.gavrilovds.restaurant.repository.CartRepository;
import ru.gavrilovds.restaurant.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final JwtProvider jwtProvider;
  private final CustomerUserDetailsService cuds;
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final CartRepository cartRepository;

  public AuthResponse createUser(SignUpRequest request) {
    UserEntity user = userRepository.findByEmail(request.email());
    if (user != null) {
      throw new UserAlreadyExistsException(request.email());
    }

    UserEntity userToCreate = new UserEntity();
    userToCreate.setFullName(request.fullName());
    userToCreate.setEmail(request.email());
    userToCreate.setRole(request.role());
    userToCreate.setPassword(passwordEncoder.encode(request.password()));

    UserEntity savedUser = userRepository.save(userToCreate);

    CartEntity cart = new CartEntity();
    cart.setCustomer(savedUser);
    cartRepository.save(cart);

    Authentication authentication = new UsernamePasswordAuthenticationToken(request.email(),
        request.password());
    SecurityContextHolder.getContext().setAuthentication(authentication);

    String jwt = jwtProvider.generateToken(authentication);

    return new AuthResponse(jwt, "Register success", savedUser.getRole().name());
  }

  public AuthResponse signIn(SignInRequest request) {
    Authentication authentication = authenticate(request.email(), request.password());

    var authorities = authentication.getAuthorities();
    var role = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();
    String jwt = jwtProvider.generateToken(authentication);

    return new AuthResponse(jwt, "Sign in success", role);
  }

  private Authentication authenticate(String email, String password) {
    UserDetails userDetails = cuds.loadUserByUsername(email);

    if (userDetails == null) {
      throw new BadCredentialsException("Invalid email %s".formatted(email));
    }

    if (!passwordEncoder.matches(password, userDetails.getPassword())) {
      throw new BadCredentialsException("Invalid password");
    }
    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
  }
}

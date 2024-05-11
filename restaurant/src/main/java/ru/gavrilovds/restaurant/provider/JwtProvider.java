package ru.gavrilovds.restaurant.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import ru.gavrilovds.restaurant.config.JwtConfig;

@Component
@RequiredArgsConstructor
public class JwtProvider {

  private final JwtConfig jwtConfig;

  public String generateToken(Authentication auth) {
    List<GrantedAuthority> authorities = (List<GrantedAuthority>) auth.getAuthorities();
    String roles = populateAuthorities(authorities);

    return Jwts.builder().setIssuedAt(new Date())
        .setExpiration(new Date(new Date().getTime() + 86400000))
        .claim("email", auth.getName())
        .claim("authorities", roles)
        .signWith(getKey())
        .compact();
  }

  public String getEmailFromJwtToken(String jwt) {
    jwt = jwt.substring(7);

    Claims claims = Jwts.parser().setSigningKey(getKey()).build().parseClaimsJws(jwt)
        .getBody();

    return String.valueOf(claims.get("email"));
  }

  private String populateAuthorities(List<GrantedAuthority> authorities) {
    Set<String> auths = new HashSet<>();
    for (GrantedAuthority authority : authorities) {
      auths.add(authority.getAuthority());
    }
    return String.join(",", auths);
  }

  private SecretKey getKey() {
    return Keys.hmacShaKeyFor(jwtConfig.getKey().getBytes(StandardCharsets.UTF_8));
  }
}

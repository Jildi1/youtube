package com.example.Youtube.Util;

import com.example.Youtube.Model.User;
import com.example.Youtube.Repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Slf4j
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration.ms}")
    private Long expirationMs;

    private final UserRepository userRepository;

    public JwtUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // берём имя из токена
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    // извлекает дату истечения срока токена
    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claim = extractAllClaim(token);
        return claimsResolver.apply(claim);
    }

    public Claims extractAllClaim(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, User user){
        final String username = extractUsername(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

    public String generateToken(User user){
        if(user.getCurrentChannel() == null){
            throw new IllegalStateException();
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("user_channel_id", user.getCurrentChannel().getId());
        claims.put("user_id", user.getId());
        return createToken(claims, user.getUsername());
    }

    public String createToken(Map<String, Object> claims, String subject){
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
        log.info(token + " from jwtutils");
        return token;
    }
}

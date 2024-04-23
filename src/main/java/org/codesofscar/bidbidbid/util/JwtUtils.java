package org.codesofscar.bidbidbid.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

@Component
public class JwtUtils {

    private Supplier<SecretKeySpec> getKey = () -> {
            Key key = Keys.hmacShaKeyFor("f203affef60b6079a96a37a3c6835f42cca1e91c963cfbe5099dd1c776e0fc4c8b2dbb6b76055255e336e940171385869d8afcf9fff80f7ce6f104a6659d3812"
                    .getBytes(StandardCharsets.UTF_8));
            return new SecretKeySpec(key.getEncoded(), key.getAlgorithm());
    };

    private Supplier<Date> expirationTime = () ->
        Date.from(LocalDateTime.now()
                .plusMinutes(60)
                .atZone(ZoneId.systemDefault())
                .toInstant());



    public Function<UserDetails, String> createJwt = (userDetails) -> {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .signWith(getKey.get())
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expirationTime.get())
                .compact();
    };


    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        Claims claims = Jwts.parser().verifyWith(getKey.get()).build()
                .parseSignedClaims(token)
                .getPayload();
        return claimResolver.apply(claims);
    }

    public Function<String, String> extractUsername = (token) -> extractClaim(token, Claims::getSubject);

    public Function<String, Date> extractExpirationTime = (token) -> extractClaim(token, Claims::getExpiration);



    public Function<String, Boolean> isTokenExpired = (token) -> extractExpirationTime.apply(token).after(new Date(System.currentTimeMillis()));

    public BiFunction<String, String, Boolean> isTokenValid = (token, username) -> isTokenExpired.apply(token) &&
            Objects.equals(extractUsername.apply(token), username);


}

package br.com.edielsonassis.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import br.com.edielsonassis.model.User;

@Service
public class TokenService {

    @Value("${security.jwt.token.secret-key}")
    private String secret;

    @Value("${security.jwt.token.expiration-length}")
    private int expirationToken;

    private static final String ISSUER = "BookRate API";

    public String generateToken(User user) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(user.getUsername())
                    .withExpiresAt(exprirationToken())
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw new SecurityException("Erro ao gerar token jwt", exception);
        }
    }

    public String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new SecurityException("Token JWT inválido ou expirado!");
        }
    }

    private Instant exprirationToken() {
        return LocalDateTime.now().plusHours(expirationToken).toInstant(ZoneOffset.of("-03:00"));
    }
}
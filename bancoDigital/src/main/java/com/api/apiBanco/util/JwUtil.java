package com.api.apiBanco.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

public class JwUtil {
    public static final String SECRET_KEY = "DigiSafeUpdate2025";
    private static final Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

    public static String generateToken(String subject, Long id, String nome) {
        return JWT.create()
                .withIssuer("BancoDigitalAPI")
                .withSubject(subject)
                .withClaim("id", id)
                .withClaim("nome", nome)
                .withIssuedAt(new Date()) // CORRETO
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600_000)) // 1h
                .sign(algorithm);
    }

    public static boolean validateToken(String token) {
        try {
            JWT.require(algorithm)
                    .withIssuer("BancoDigitalAPI")
                    .build()
                    .verify(token); // vai lançar exceção se for inválido
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

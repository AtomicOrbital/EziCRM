package com.example.crm.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtHelper {

    @Value("${token.secret.keys}")
    private String strKey;

    private final long timeExpired = 8 * 60 * 60 * 1000;
    public String generateToken(String data){
//        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//        String strKey = Encoders.BASE64.encode(key.getEncoded());
//        System.out.println("Kiem tra: " + strKey);

        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(strKey));
        Date currentDate = new Date();
        long miliCurrentDate = currentDate.getTime();
        long miliFuture = miliCurrentDate + timeExpired;
        Date futureDate = new Date(miliFuture);

        String token = Jwts.builder()
                .signWith(key)
                .setSubject(data)
                .setExpiration(futureDate)
                .compact();

        return token;
    }

    public String validationToken(String token){
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(strKey));
        return  Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)// truyền vào token cần giải mã
                .getBody()
                .getSubject();

    }
}

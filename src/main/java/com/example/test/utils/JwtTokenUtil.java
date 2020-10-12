package com.example.test.utils;

import com.example.test.pojo.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTokenUtil {

    public static final long EXPIRITION = 1000 * 15;//15秒
    public static final String APPSECRET_KEY = "huang0715";
    public static final String USER_ID = "userId";
    public static final String USERNAME = "username";

    public static String createToken(User user){
        String token = Jwts
                .builder()
                .setSubject(Long.toString(user.getId()))
                .claim(USER_ID,user.getId())
                .claim(USERNAME,user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRITION))
                .signWith(SignatureAlgorithm.HS512,APPSECRET_KEY)
                .compact();
        return token;
    }

}

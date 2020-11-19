package sustech.dbojbackend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import sustech.dbojbackend.model.data.User;

import java.util.Date;

public class Token {
    private static final String tempKey = "abcdefg";
    private static final long TOKEN_EXPIRED_TIME = 100000;

    public static String createToken(User user) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();

        JwtBuilder builder = Jwts.builder()
                .setId(Long.toString(user.getId()))
                .setIssuedAt(new Date(nowMillis))
                .setNotBefore(new Date(nowMillis))
                .setExpiration(new Date(nowMillis + TOKEN_EXPIRED_TIME))
                .signWith(signatureAlgorithm, tempKey);
        builder.claim("username", user.getUserName());
        return builder.compact();
    }

    public static boolean checkToken(String token,String userName,Long userID) {
        try {
            Claims claims = Jwts.parser().setSigningKey(tempKey).parseClaimsJws(token.trim()).getBody();
            String name=claims.get("username",String.class);
            long id= Long.parseLong(claims.getId());
            return name.equals(userName) && id==userID;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

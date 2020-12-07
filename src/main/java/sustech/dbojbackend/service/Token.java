package sustech.dbojbackend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import sustech.dbojbackend.model.UserLevel;
import sustech.dbojbackend.model.data.User;
import sustech.dbojbackend.repository.UserRepository;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

@Service
public class Token {
    private static final String Issuer = "SUSTechDbojBackground";
    private static final String tempKey = "ThisIsTheTempKeyOfTokenProduce";
    private static final long TOKEN_EXPIRED_TIME = 60 * 60 * 24 * 1000 * 2; // 2 days
    private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    @Resource
    UserRepository userRepository;

    public String createToken(User user) {
        long nowMillis = System.currentTimeMillis();
        JwtBuilder builder = Jwts.builder()
                .claim("username", user.getUserName())
                .setIssuedAt(new Date(nowMillis))
                .setNotBefore(new Date(nowMillis))
                .setExpiration(new Date(nowMillis + TOKEN_EXPIRED_TIME))
                .setIssuer(Issuer)
                .setSubject("TokenOfDboj")
                .signWith(signatureAlgorithm, tempKey);
        return builder.compact();
    }

    public String createTokenWithNewPassWord(User user, String newPassword) {
        long nowMillis = System.currentTimeMillis();
        var builder = Jwts.builder()
                .claim("username", user.getUserName())
                .claim("newPassword", newPassword)
                .setIssuedAt(new Date(nowMillis))
                .setNotBefore(new Date(nowMillis))
                .setExpiration(new Date(nowMillis + TOKEN_EXPIRED_TIME))
                .setIssuer(Issuer)
                .setSubject("TokenOfDboj")
                .signWith(signatureAlgorithm, tempKey);
        return builder.compact();
    }

    public String gettokenUserName(String token) {
        return Jwts.parser().setSigningKey(tempKey).parseClaimsJws(token.trim()).getBody().get("username", String.class);
    }
    public String gettokenpassword(String token) {
        return Jwts.parser().setSigningKey(tempKey).parseClaimsJws(token.trim()).getBody().get("newPassword", String.class);
    }
    public UserLevel checkToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(tempKey).parseClaimsJws(token.trim()).getBody();
            String name = claims.get("username", String.class);
            List<User> users = userRepository.findByUserName(name);
            if (users.isEmpty()) {
                return null;
            } else {
                return users.get(0).getLevel();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

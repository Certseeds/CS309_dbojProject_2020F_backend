package sustech.dbojbackend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import sustech.dbojbackend.model.data.User;
import sustech.dbojbackend.repository.UserRepository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
@Service
public class Token {

    private final UserRepository userRepository;

    private static final String tempKey = "abcdefg";
    private static final long TOKEN_EXPIRED_TIME = 100000;

    public Token(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public String createToken(User user) {
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

    public User checkToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(tempKey).parseClaimsJws(token.trim()).getBody();
            String name = claims.get("username", String.class);
            Long id = Long.parseLong(claims.getId());
            List<User> users = userRepository.findByid(id);
            User u=users.get(0);
            if (u != null && !u.getUserName().equals(name)) {
                throw new RuntimeException("ID and name not match");
            }
            return u;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("invalid token");
        }
    }
}

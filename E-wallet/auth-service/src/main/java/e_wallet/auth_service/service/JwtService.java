package e_wallet.auth_service.service;

import e_wallet.user_service.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {
    private final SecretKey secretKey = Keys.hmacShaKeyFor("your-256-bit-secret-should-be-long-enough".getBytes(StandardCharsets.UTF_8));

//    Generate jwt token
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 *24))
                .signWith(secretKey)
                .compact();
    }


//    Parse username
    public String parseUserName(String token) {
        Claims claims =  Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    //    Validate jwt token and save user detail to SecurityContext
    public boolean validateToken(String token, User user) {
        String extracted = parseUserName(token);
        return user.getEmail().equals(extracted);
    }
}

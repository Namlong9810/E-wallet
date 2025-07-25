package e_wallet.shared_module.config;

import e_wallet.shared_module.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;

@Component
public class JwtTokenProvider {
    private final SecretKey secretKey = Keys.hmacShaKeyFor("your-256-bit-secret-should-be-long-enough".getBytes(StandardCharsets.UTF_8));

//    Generate jwt token
    public String generateToken(User user) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("user id", user.getUser_id());
        claims.put("phone", user.getPhone());
        claims.put("address", user.getAddress());

        return Jwts.builder()
                .setClaims(claims)
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
    public boolean validateToken(String token, UserDetails user) {
        String extracted = parseUserName(token);
        return user.getUsername().equals(extracted);
    }
}

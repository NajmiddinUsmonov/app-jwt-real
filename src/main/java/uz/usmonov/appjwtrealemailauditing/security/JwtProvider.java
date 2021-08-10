package uz.usmonov.appjwtrealemailauditing.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import uz.usmonov.appjwtrealemailauditing.entity.Role;

import java.util.Date;
import java.util.Set;

@Component
public class JwtProvider {

    private static final long expiredTime=1000*60*60*24;
    private static final String secretKey="thisissecretkey";

    public String generateToken(String username, Set<Role> roles){
        Date expiretDate = new Date ( System.currentTimeMillis () + expiredTime );

        String token = Jwts
                .builder ()
                .setSubject ( username )
                .setIssuedAt ( new Date () )
                .setExpiration ( expiretDate )
                .signWith ( SignatureAlgorithm.HS512, secretKey )
                .claim ( "role", roles )
                .compact ();

        return token;
    }

    public String getEmailFromToken(String token){
        try {
            String email = Jwts
                    .parser ()
                    .setSigningKey ( secretKey )
                    .parseClaimsJws ( token )
                    .getBody ()
                    .getSubject ();
            return email;
        }catch (Exception e){
            return null;
        }
    }
}

package security.java.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.*;

public class TokensUtils {
    private final static String ACCESS_TOKEN_SECRET = "asdasdq4we64561as231dq6w5e426as1d"; // Clave secreta
    private final static Long ACCESS_TOKEN_VALIDITY_SECONDS = 2_592_00L;  // Expiración del token en segundos

    // Crear token
    public static String createToken(String nombre, String email){
        long expirationTime = ACCESS_TOKEN_VALIDITY_SECONDS * 1000;  // Convertir a milisegundos
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        Map<String, Object> extra = new HashMap<>();
        extra.put("nombre", nombre);  // Agregar datos extra al token

        return Jwts.builder()
                .setSubject(email)  // Establecer el "subject" como el email
                .setExpiration(expirationDate)  // Establecer la fecha de expiración
                .addClaims(extra)  // Agregar datos extra al token
                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))  // Usar la clave generada
                .compact();
    }

    // Obtener autenticación del token
    public static UsernamePasswordAuthenticationToken getAuthentication(String token){
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(ACCESS_TOKEN_SECRET.getBytes())// Usar la clave correctamente
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String email = claims.getSubject();  // Obtener el email del "subject"

            return new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
        } catch (JwtException e) {
            return null;  // Si hay error con el token, retornar null
        }
    }
}

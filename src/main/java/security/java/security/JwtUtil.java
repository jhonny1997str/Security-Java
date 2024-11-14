package security.java.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    // Generar el token con una clave secreta segura
    public String generarToken(Authentication authentication) {
        // Generar una clave secreta de 512 bits de forma segura
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

        // Construir el token JWT
        return Jwts.builder()
                .setSubject(authentication.getName())   // Usamos el nombre de usuario para el 'subject'
                .setIssuedAt(new Date())                 // Establecemos la fecha de emisión
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))  // Establecemos la expiración
                .signWith(secretKey)  // Usamos la clave secreta generada
                .compact();
    }

    // Obtener el usuario del token (actualizado con el parserBuilder)
    public String obtenerUsuarioDelToken(String token) {
        try {
            Claims claims = Jwts.parser()  // Usamos el parserBuilder
                    .setSigningKey(Keys.secretKeyFor(SignatureAlgorithm.HS512))  // Establecemos la clave secreta
                    .build()
                    .parseClaimsJws(token)      // Parseamos el token
                    .getBody();
            return claims.getSubject();  // Retorna el 'username' o el 'email'
        } catch (Exception e) {
            return null;  // Si el token es inválido o ha expirado, devuelve null
        }
    }
}

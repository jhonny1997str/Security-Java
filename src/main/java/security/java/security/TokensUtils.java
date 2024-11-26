package security.java.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.*;

public class TokensUtils {

    // Clave secreta utilizada para firmar los tokens. En un entorno real, debe ser guardada de forma segura.
    private final static String ACCESS_TOKEN_SECRET = "asdasdq4we64561as231dq6w5e426as1d"; // Clave secreta

    // Tiempo de validez del token en segundos (24 horas en este caso).
    private final static Long ACCESS_TOKEN_VALIDITY_SECONDS = 2_592_00L;  // Expiración del token en segundos

    // Método para crear un token JWT
    public static String createToken(String nombre, String email) {

        // Tiempo de expiración del token en milisegundos
        long expirationTime = ACCESS_TOKEN_VALIDITY_SECONDS * 1000;  // Convertir a milisegundos

        // Crear un objeto Date con la fecha de expiración del token
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        // Crear un mapa para agregar datos adicionales al token (como el nombre en este caso)
        Map<String, Object> extra = new HashMap<>();
        extra.put("nombre", nombre);  // Agregar datos extra al token (el nombre del usuario)

        // Crear y devolver el token JWT
        return Jwts.builder()
                .setSubject(email)  // Establecer el "subject" del token, que en este caso es el email del usuario.
                .setExpiration(expirationDate)  // Establecer la fecha de expiración del token
                .addClaims(extra)  // Agregar los datos extra al token
                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))  // Firmar el token con la clave secreta utilizando el algoritmo HMAC-SHA
                .compact();  // Crear el token compactado como un string
    }

    // Método para obtener la autenticación del token JWT
    public static UsernamePasswordAuthenticationToken getAuthentication(String token) {

        try {
            // Parsear el token y obtener los "claims" (reclamaciones) del JWT
            Claims claims = Jwts.parser()
                    .setSigningKey(ACCESS_TOKEN_SECRET.getBytes())  // Establecer la clave secreta para verificar la firma del token
                    .build()
                    .parseClaimsJws(token)  // Parsear el token JWT
                    .getBody();  // Obtener el cuerpo de las reclamaciones (claims)

            // Obtener el "subject" del token, que es el email del usuario
            String email = claims.getSubject();  // El "subject" es el email que se utilizó para crear el token

            // Crear y devolver un objeto de autenticación con el email del usuario (sin contraseña, ya que el token es suficiente para autenticarse)
            return new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
        } catch (JwtException e) {
            // Si ocurre un error con el token (por ejemplo, si es inválido o ha expirado), devolver null
            return null;
        }
    }
}
//La clase TokensUtils se encarga de generar tokens JWT y de extraer la autenticación de dichos tokens.
// Los tokens incluyen información como el email del usuario y una fecha de expiración.
// La autenticación se verifica al analizar el token con la clave secreta y recuperar el email para crear
// un objeto de autenticación. Esto es útil para aplicaciones basadas en tokens donde no se usa estado de sesión
// en el servidor.

//TokensUtils
//Propósito: Esta clase proporciona utilidades para manejar los tokens JWT (JSON Web Tokens). En este caso,
// se utiliza para crear y verificar tokens JWT.
//¿Qué hace?:
//createToken(String nombre, String email): Crea un token JWT con el nombre y el email del usuario, junto con una
// fecha de expiración. Este token es lo que se usará para autenticar al usuario en futuras peticiones.
//getAuthentication(String token): Extrae y verifica el token JWT. Si el token es válido, extrae el email del
// "subject" del token y devuelve un UsernamePasswordAuthenticationToken que Spring Security puede usar para
// autenticar al usuario.
//¿Por qué es importante?: TokensUtils gestiona los detalles técnicos de la creación y validación de los tokens JWT,
//  que es la base para la autenticación sin estado en una API REST.
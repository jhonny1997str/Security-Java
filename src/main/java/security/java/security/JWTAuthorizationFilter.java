package security.java.security;

// Importaciones necesarias para trabajar con filtros, seguridad y manejo de solicitudes HTTP
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Indica que esta clase es un componente de Spring y será detectada automáticamente en la configuración del contexto de Spring
@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {
    // Esta clase extiende OncePerRequestFilter para asegurarse de que el filtro se ejecuta una vez por solicitud

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Método que se ejecutará cada vez que pase una solicitud HTTP por el filtro

        // Obtiene el encabezado "Authorization" de la solicitud
        String bearerToken = request.getHeader("Authorization");

        // Verifica si el encabezado no es nulo y si comienza con "Bearer "
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            // Extrae el token JWT eliminando el prefijo "Bearer "
            String token = bearerToken.replace("Bearer ", "");

            // Llama a una utilidad para obtener un objeto de autenticación basado en el token
            // `TokensUtils.getAuthentication(token)` es un método externo que valida el token
            // y devuelve un UsernamePasswordAuthenticationToken si es válido
            UsernamePasswordAuthenticationToken usernamePAT = TokensUtils.getAuthentication(token);

            // Establece el objeto de autenticación en el contexto de seguridad de Spring
            // Esto permite que el token autentique al usuario durante la solicitud
            SecurityContextHolder.getContext().setAuthentication(usernamePAT);
        }

        // Continúa con el siguiente filtro en la cadena, o procesa la solicitud si es el último filtro
        filterChain.doFilter(request, response);
    }
}


//JWTAuthorizationFilter extends OncePerRequestFilter
//Propósito: Este filtro se encarga de autorizar las peticiones entrantes. Extiende de OncePerRequestFilter para
// garantizar que solo se ejecute una vez por solicitud.
//¿Qué hace?:
//Este filtro verifica que el token JWT esté presente en las cabeceras de la solicitud
// (generalmente en el encabezado Authorization).
//Si el token es válido, lo decodifica, extrae la información del usuario y autoriza la petición.
//¿Por qué es importante?: Este filtro es clave para verificar si el usuario tiene un token válido antes de
// permitir el acceso a los recursos protegidos.

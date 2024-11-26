package security.java.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collections;

//La clase JWTAuthenticationFilter extiende de UsernamePasswordAuthenticationFilter, que es un filtro
// estándar de Spring Security utilizado para manejar la autenticación basada en nombre de usuario y contraseña.
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    // Sobrescribe el método attemptAuthentication de UsernamePasswordAuthenticationFilter
    // Este método se ejecuta cuando se intenta autenticar a un usuario con sus credenciales
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        // Crea un objeto AuthCredentials donde se almacenarán las credenciales del usuario
        AuthCredentials authCredentials = new AuthCredentials();

        // Intenta leer las credenciales del cuerpo de la solicitud (request) como un JSON
        try {
            // Usa ObjectMapper para convertir el cuerpo de la solicitud en un objeto de tipo AuthCredentials
            authCredentials = new ObjectMapper().readValue(request.getReader(), AuthCredentials.class);
        } catch (IOException e) {
            // Si ocurre un error durante el proceso de lectura, lo captura (sin hacer nada aquí)
        }

        // Crea un objeto UsernamePasswordAuthenticationToken, que es lo que Spring Security usa
        // para realizar la autenticación del usuario, pasando el correo y la contraseña
        // Además, se pasan los roles vacíos por ahora (Collections.emptyList()).
        UsernamePasswordAuthenticationToken usernamePAT = new UsernamePasswordAuthenticationToken(
                authCredentials.getEmail(),
                authCredentials.getPassword(),
                Collections.emptyList()
        );

        // Usa el AuthenticationManager de Spring Security para autenticar al usuario con el token creado
        return getAuthenticationManager().authenticate(usernamePAT);
    }

    // Sobrescribe el método successfulAuthentication de UsernamePasswordAuthenticationFilter
    // Este método se ejecuta si la autenticación es exitosa
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        // Obtiene el objeto UserDetails (información del usuario autenticado)
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();

        // Crea un token JWT usando la clase TokensUtils, pasando el nombre y el username del usuario
        String token = TokensUtils.createToken(userDetails.getNombre(), userDetails.getUsername());

        // Agrega el token JWT en el encabezado de la respuesta HTTP con el prefijo "Bearer"
        response.addHeader("Authorization", "Bearer " + token);

        // Vacía el cuerpo de la respuesta, asegurando que se envíe el encabezado con el token
        response.getWriter().flush();

        // Llama al método de éxito de la clase padre para continuar con el proceso de autenticación
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
//esta clase es crucial en una implementación de autenticación JWT porque maneja la autenticación y
// autorización del usuario de manera eficiente y segura, permitiendo que la aplicación funcione de manera
// más escalable y robusta.

//JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter
//Propósito: Este filtro maneja el proceso de autenticación de los usuarios, validando las credenciales
// proporcionadas en el formulario de login o en la petición.
//¿Qué hace?:
//Recibe las credenciales del usuario (en este caso, el email y la contraseña).
//Si las credenciales son correctas, genera un token JWT para el usuario.
//¿Por qué es importante?: Es el primer filtro que se encarga de autenticar al usuario en el sistema, generando
// el token que luego se usará para autenticar en peticiones posteriores.

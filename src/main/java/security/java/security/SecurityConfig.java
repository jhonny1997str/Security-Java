package security.java.security;

import lombok.AllArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // Marca la clase como una clase de configuración de Spring.
@EnableWebSecurity // Habilita la configuración de seguridad para una aplicación web.
@AllArgsConstructor // Genera automáticamente un constructor con todos los parámetros que esta clase requiere.
public class SecurityConfig {

    private final UserDetailsService userDetailsService; // Servicio para cargar los detalles del usuario.
    private final JWTAuthorizationFilter jwtAuthorizationFilter; // Filtro de autorización basado en JWT.

    // Configuración de seguridad de la aplicación. El HttpSecurity se utiliza para configurar el flujo de seguridad.
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {
        // Crear una instancia del filtro de autenticación basado en JWT
        JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter();
        jwtAuthenticationFilter.setAuthenticationManager(authManager); // Asocia el AuthenticationManager al filtro.
        jwtAuthenticationFilter.setFilterProcessesUrl("/login"); // Establece la URL de la solicitud de autenticación.

        return http
                .csrf().disable() // Desactiva la protección CSRF (no necesaria en APIs RESTful).
                .authorizeRequests() // Comienza la configuración de autorización para las solicitudes.
                .anyRequest() // Aplica la regla a todas las solicitudes.
                .authenticated() // Asegura que todas las solicitudes deben estar autenticadas.
                .and()
                .httpBasic() // Habilita la autenticación básica HTTP (opcional, solo para pruebas).
                .and()
                .sessionManagement() // Configura cómo se manejarán las sesiones.
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No se crea sesión, se utiliza JWT.
                .and()
                .addFilter(jwtAuthenticationFilter) // Añade el filtro JWT de autenticación.
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class) // Añade el filtro JWT de autorización antes del filtro de autenticación estándar.
                .build(); // Devuelve el filtro de seguridad configurado.
    }

    // Configura el AuthenticationManager que se usará para autenticar al usuario.
    @Bean
    AuthenticationManager authManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class) // Obtiene el builder de AuthenticationManager.
                .userDetailsService(userDetailsService) // Asocia el UserDetailsService al manager para cargar los detalles del usuario.
                .passwordEncoder(passwordEncoder()) // Asocia el codificador de contraseñas.
                .and()
                .build(); // Construye y devuelve el AuthenticationManager.
    }

    // Configura el codificador de contraseñas utilizando BCrypt.
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); // Devuelve una instancia de BCryptPasswordEncoder, utilizado para encriptar contraseñas.
    }

    // Método main para generar una contraseña codificada utilizando BCrypt.
    public static void main(String[] args) {
        // Imprime una versión encriptada de la contraseña "solocali".
        System.out.println("pass" + new BCryptPasswordEncoder().encode("solocali"));
    }
}

//Esta clase (SecurityConfig) configura la seguridad de la aplicación utilizando Spring Security y JWT.
// Las funcionalidades clave incluyen:

//Autenticación y autorización mediante JWT, asegurando que solo los usuarios autenticados puedan acceder a las rutas protegidas.
//Codificación de contraseñas con BCrypt para mantener la seguridad de las credenciales del usuario.
//Desactivación de sesiones (SessionCreationPolicy.STATELESS), ya que en aplicaciones basadas en JWT no se necesita mantener sesiones en el servidor.
//Intercepción de las solicitudes para autenticar al usuario y autorizar el acceso a las rutas protegidas.

// SecurityConfig
//Propósito: Esta clase configura la seguridad en tu aplicación, utilizando Spring Security. Su principal tarea es definir las reglas de seguridad, la autenticación y la autorización.
//¿Qué hace?:
//Configura los filtros de seguridad para manejar la autenticación y autorización, especialmente con JWT.
//Define las políticas de sesión (SessionCreationPolicy.STATELESS), lo que significa que no se manejarán sesiones en el servidor (ideal para una API RESTful).
//Registra los filtros para el manejo de JWT: JWTAuthenticationFilter (autenticación) y JWTAuthorizationFilter (autorización).
//Establece un AuthenticationManager para autenticar a los usuarios mediante su correo electrónico y contraseña.
//¿Por qué es importante?: Sin esta clase, no se tendrían configuradas las reglas y filtros necesarios para autenticar y autorizar a los usuarios correctamente.
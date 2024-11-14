package security.java.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import security.java.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Lazy;  // Importar Lazy

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UsuarioService usuarioService;

    public SecurityConfig(@Lazy UsuarioService usuarioService) {  // Usamos @Lazy aquí
        this.usuarioService = usuarioService;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(usuarioService)  // Ahora usamos el servicio UsuarioService específico
                .passwordEncoder(passwordEncoder());  // Configuración para el codificador de contraseñas

        return authenticationManagerBuilder.build();  // Construimos y devolvemos el AuthenticationManager
    }


    // Definir un PasswordEncoder para la codificación de contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Usamos BCryptPasswordEncoder para la codificación de contraseñas
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .requestMatchers("/api/auth/**").permitAll()  // Rutas públicas
                .anyRequest().authenticated()  // Rutas privadas requieren autenticación
                .and()
                .csrf(csrf -> csrf.disable())  // Nueva forma de deshabilitar CSRF
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));  // Nueva forma de configurar la política de sesiones

        return http.build();
    }
}

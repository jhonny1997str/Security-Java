package security.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import security.java.models.Contactos;
import security.java.models.Usuario;

import java.util.Optional;

@Repository
public interface ContactoRepository extends JpaRepository <Contactos, Integer> {

}

//Resumen de la Arquitectura
//En resumen, estas clases y filtros forman una parte importante de un sistema de autenticación basado en JWT.
// El flujo es el siguiente:
//
//Usuario inicia sesión: El JWTAuthenticationFilter maneja la autenticación y genera un token JWT.
//Petición protegida: El JWTAuthorizationFilter valida que la solicitud tenga un token JWT y autoriza la petición
// si el token es válido.
//Detalles del Usuario: El UserDetailServiceIml busca y carga los detalles del usuario desde la base de datos
// usando el email.
//UserDetailsImpl: Representa al usuario y sus detalles dentro del sistema de seguridad de Spring.
//El flujo asegura que solo los usuarios autenticados (a través del token JWT) puedan acceder a los recursos
// protegidos.
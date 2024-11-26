package security.java.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import security.java.models.Usuario;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

// Anotación que genera un constructor con todos los parámetros de la clase
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    // Atributo que almacena la instancia del modelo Usuario
    private final Usuario usuario;

    @Override
    // Devuelve las autoridades o roles del usuario (en este caso, no se gestionan roles)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();  // Se devuelve una lista vacía ya que no se gestionan roles en este caso
    }

    @Override
    // Devuelve la contraseña del usuario
    public String getPassword() {
        return usuario.getPassword();  // Obtiene la contraseña del objeto Usuario
    }

    @Override
    // Devuelve el nombre de usuario, que en este caso es el email
    public String getUsername() {
        return usuario.getEmail();  // Obtiene el email del objeto Usuario
    }

    @Override
    // Verifica si la cuenta no ha expirado (siempre retorna true, ya que no se gestiona expiración de cuenta)
    public boolean isAccountNonExpired() {
        return true;  // Devuelve true, indicando que la cuenta no está expirada
    }

    @Override
    // Verifica si la cuenta no está bloqueada (siempre retorna true, ya que no se gestiona bloqueo de cuenta)
    public boolean isAccountNonLocked() {
        return true;  // Devuelve true, indicando que la cuenta no está bloqueada
    }

    @Override
    // Verifica si las credenciales (contraseña) no han expirado (siempre retorna true, ya que no se gestiona expiración de credenciales)
    public boolean isCredentialsNonExpired() {
        return true;  // Devuelve true, indicando que las credenciales no han expirado
    }

    @Override
    // Verifica si el usuario está habilitado (siempre retorna true, ya que no se gestiona la habilitación)
    public boolean isEnabled() {
        return true;  // Devuelve true, indicando que el usuario está habilitado
    }

    // Método adicional que devuelve el nombre del usuario
    public String getNombre() {
        return usuario.getNombre();  // Obtiene el nombre del objeto Usuario
    }
}

//UserDetailsImpl se utiliza para proporcionar los detalles del usuario durante el proceso de autenticación
// en Spring Security. Spring Security utiliza la información proporcionada por esta clase
// (nombre de usuario, contraseña, roles, etc.) para gestionar la autenticación y autorización del usuario.

//UserDetailsImpl implements UserDetails
//Propósito: Esta clase implementa la interfaz UserDetails, que es utilizada por Spring Security para manejar la
// información del usuario (como nombre, contraseñas, roles, etc.) durante el proceso de autenticación.
//¿Qué hace?:
//getUsername(): Devuelve el nombre de usuario (en este caso, el email del usuario).
//getPassword(): Devuelve la contraseña cifrada del usuario.
//getAuthorities(): Devuelve los roles o permisos del usuario, aunque en este caso se retorna una lista vacía,
// lo que indica que no se están manejando roles.
//Métodos de estado de la cuenta: Métodos como isAccountNonExpired(), isAccountNonLocked(), isEnabled(), etc.,
// siempre retornan true, lo que significa que la cuenta está activa y no está bloqueada.
//¿Por qué es importante?: Esta clase es esencial para representar al usuario dentro de la seguridad de Spring,
// ya que UserDetails es un contrato que Spring Security espera para autenticar y autorizar a los usuarios.
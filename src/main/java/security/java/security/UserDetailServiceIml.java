package security.java.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import security.java.models.Usuario;
import security.java.repository.UsuarioRepository;

@Service  // Indica que esta clase es un servicio de Spring y se gestiona a través del contexto de Spring
public class
UserDetailServiceIml implements UserDetailsService {  // Implementa la interfaz UserDetailsService de Spring Security

    @Autowired  // Se inyecta automáticamente una instancia del repositorio UsuarioRepository
    private UsuarioRepository usuarioRepository;

    @Override  // Sobrescribe el método loadUserByUsername, que busca un usuario por su nombre de usuario (en este caso, el email)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // Busca un usuario en la base de datos utilizando el email. Si no lo encuentra, lanza una excepción
        Usuario usuario = usuarioRepository
                .findOneByEmail(email)  // Método que busca al usuario por su email
                .orElseThrow(() -> new UsernameNotFoundException("El usuario con email " + email + " no existe"));  // Lanza una excepción si no se encuentra el usuario

        // Si el usuario es encontrado, crea una instancia de UserDetailsImpl (que implementa UserDetails) con la información del usuario
        return new UserDetailsImpl(usuario);
    }
}
//La clase UserDetailServiceIml implementa la interfaz UserDetailsService que Spring Security usa para cargar
// los detalles del usuario durante el proceso de autenticación. Este servicio se conecta a la base de datos
// para obtener un usuario en función del email, y luego devuelve una instancia de UserDetailsImpl,
// que es utilizada por Spring Security para autenticar al usuario.

//UserDetailServiceIml implements UserDetailsService
//Propósito: Esta clase implementa la interfaz UserDetailsService, que es responsable de cargar la información
// del usuario desde una fuente de datos (como una base de datos) mediante el nombre de usuario.
//¿Qué hace?:
//loadUserByUsername(String email): Este método busca al usuario en la base de datos a través del email, y si no
// lo encuentra, lanza una excepción UsernameNotFoundException. Si el usuario existe, se crea un objeto
// UserDetailsImpl con los detalles del usuario.
//¿Por qué es importante?: Es fundamental porque Spring Security depende de este servicio para obtener los detalles
// del usuario durante el proceso de autenticación. En este caso, se busca al usuario por su correo electrónico.
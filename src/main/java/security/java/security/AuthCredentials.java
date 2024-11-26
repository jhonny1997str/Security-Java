package security.java.security;

// Importa la anotación @Data de la biblioteca Lombok.
// Lombok es una herramienta que ayuda a reducir el código repetitivo, como getters, setters, etc.
import lombok.Data;

// Anotación @Data de Lombok:
// - Genera automáticamente getters, setters, toString(), hashCode(), equals() y otros métodos.
// - Facilita el manejo de clases "planas" (POJOs).
@Data
public class AuthCredentials { // Declaración de la clase pública llamada AuthCredentials.

    // Campo rivado para almacenar el correo electrónico del usuario.
    // La anotación @Data generará el método getEmail() y setEmail().
    private String email;

    // Campo privado para almacenar la contraseña del usuario.
    // La anotación @Data también generará automáticamente el método getPassword() y setPassword().
    private String password;
}

//La clase AuthCredentials sirve como modelo para almacenar los datos de
// autenticación de un usuario.

//AuthCredentials
//Propósito: Esta clase generalmente se usa para representar las credenciales de autenticación que el usuario
// envía al sistema (por ejemplo, su email y contraseña).
//¿Qué hace?:
//Almacena los datos de entrada de la autenticación, como el email y la contraseña, que luego serán validados en el
// proceso de login.
//¿Por qué es importante?: Aunque no se muestra completamente, esta clase facilita la separación de las credenciales
// de los usuarios y puede utilizarse para hacer una validación más clara y estructurada de los datos.
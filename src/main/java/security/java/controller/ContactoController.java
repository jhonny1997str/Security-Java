package security.java.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import security.java.models.Contactos;
import security.java.repository.ContactoRepository;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("contactos")
public class ContactoController {
    private  final ContactoRepository contactoRepository;

    @GetMapping
    public List<Contactos> listcontactos(){
        return contactoRepository.findAll();
    }
}
//Estas clases son la base de la configuración de seguridad y la autenticación utilizando JWT y Spring Security.
// La configuración de seguridad se maneja en SecurityConfig, mientras que TokensUtils gestiona la creación y
// verificación de los tokens JWT. El servicio de detalles del usuario UserDetailServiceIml y la implementación
// de UserDetailsImpl son cruciales para la carga de datos del usuario desde la base de datos.
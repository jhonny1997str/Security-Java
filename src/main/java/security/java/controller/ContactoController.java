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

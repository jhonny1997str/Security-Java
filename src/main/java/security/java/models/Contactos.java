package security.java.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "contactos")
public class Contactos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contacto")
    private Integer contacto_id;

    private String nombre;
    private String email;

}

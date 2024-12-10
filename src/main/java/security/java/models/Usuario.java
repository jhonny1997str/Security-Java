package security.java.models;



import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "usuarios")
public class Usuario  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer customer_id;

    private String nombre;
    private String email;
    private String password;

}

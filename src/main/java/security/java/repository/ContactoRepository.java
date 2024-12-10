package security.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import security.java.models.Contactos;


@Repository
public interface ContactoRepository extends JpaRepository <Contactos, Integer> {

}

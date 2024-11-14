package security.java.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customer_id;

    private String customer_name;
    private String email;
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Si decides usar roles, deberías retornar las autoridades aquí.
        return null;  // En este caso, no estamos usando roles, por lo que es null.
    }

    @Override
    public String getUsername() {
        return this.email;  // Usamos el email como nombre de usuario
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // El estado de la cuenta está activo.
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // La cuenta no está bloqueada.
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Las credenciales no están expiradas.
    }

    @Override
    public boolean isEnabled() {
        return true;  // El usuario está habilitado.
    }
}

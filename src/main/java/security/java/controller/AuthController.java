package security.java.controller;

import security.java.models.Usuario;
import security.java.security.JwtUtil;
import security.java.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;  // Inyectamos el servicio de Usuario

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UsuarioService usuarioService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.usuarioService = usuarioService;  // Inicializamos el servicio
    }

    // Método de login
    @PostMapping("/login")
    public String login(@RequestBody Usuario usuario) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getPassword()));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Generar y devolver el token JWT
        return jwtUtil.generarToken(authentication);  // Se pasa directamente el objeto `authentication`
    }

    // Método para registrar un nuevo usuario
    @PostMapping("/register")
    public String register(@RequestBody Usuario usuario) {
        // Verificamos si el usuario ya existe por su correo electrónico
        if (usuarioService.buscarPorEmail(usuario.getEmail()).isPresent()) {
            return "Error: El usuario con el correo " + usuario.getEmail() + " ya existe.";
        }

        // Guardamos al nuevo usuario, codificando su contraseña antes de guardar
        usuarioService.guardarUsuario(usuario);

        // Retornamos un mensaje de éxito (puedes devolver un token también si lo prefieres)
        return "Usuario registrado exitosamente";
    }
}

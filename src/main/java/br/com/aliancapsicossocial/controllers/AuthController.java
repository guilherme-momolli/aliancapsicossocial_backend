package br.com.aliancapsicossocial.controllers;

import br.com.aliancapsicossocial.dtos.LoginRequestDTO;
import br.com.aliancapsicossocial.dtos.LoginResponseDTO;
import br.com.aliancapsicossocial.dtos.RegisterRequestDTO;
import br.com.aliancapsicossocial.models.Usuario;
import br.com.aliancapsicossocial.repositories.UsuarioRepository;
import br.com.aliancapsicossocial.services.TokenService;
import br.com.aliancapsicossocial.exceptions.RegraDeNegocioException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository repository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, UsuarioRepository repository,
                          TokenService tokenService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.repository = repository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        
        var usuario = (Usuario) auth.getPrincipal();
        var token = tokenService.gerarToken(usuario);

        return ResponseEntity.ok(new LoginResponseDTO(token, usuario.getEmail(), usuario.getRole().name()));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequestDTO data) {
        if (this.repository.findByEmail(data.email()).isPresent()) {
            throw new RegraDeNegocioException("O e-mail informado já está cadastrado no sistema.");
        }

        String encryptedPassword = passwordEncoder.encode(data.senha());
        Usuario novoUsuario = Usuario.builder()
                .email(data.email())
                .senha(encryptedPassword)
                .role(data.role())
                .build();

        this.repository.save(novoUsuario);

        return ResponseEntity.ok().build();
    }
}

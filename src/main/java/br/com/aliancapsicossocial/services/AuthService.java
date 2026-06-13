package br.com.aliancapsicossocial.services;

import br.com.aliancapsicossocial.dtos.auth.*;
import br.com.aliancapsicossocial.exceptions.RegraDeNegocioException;
import br.com.aliancapsicossocial.models.Usuario;
import br.com.aliancapsicossocial.repositories.UsuarioRepository;
import br.com.aliancapsicossocial.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public TokenResponseDTO login(LoginRequestDTO request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.senha())
        );

        Usuario usuario = (Usuario) auth.getPrincipal();

        if (usuario.isTwoFactorEnabled()) {
            String code = String.format("%06d", new Random().nextInt(999999));
            usuario.setTwoFactorCode(code);
            usuario.setTwoFactorExpiry(LocalDateTime.now().plusMinutes(5));
            usuarioRepository.save(usuario);

            log.info("MOCK EMAIL: Código 2FA para {}: {}", usuario.getEmail(), code);
            return new TokenResponseDTO(null, null, true);
        }

        return generateTokenResponse(usuario);
    }

    @Transactional
    public TokenResponseDTO verify2FA(TwoFactorVerifyDTO request) {
        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado"));

        if (usuario.getTwoFactorCode() == null || !usuario.getTwoFactorCode().equals(request.codigo())) {
            throw new RegraDeNegocioException("Código 2FA inválido");
        }

        if (usuario.getTwoFactorExpiry().isBefore(LocalDateTime.now())) {
            throw new RegraDeNegocioException("Código 2FA expirado");
        }

        usuario.setTwoFactorCode(null);
        usuario.setTwoFactorExpiry(null);
        usuarioRepository.save(usuario);

        return generateTokenResponse(usuario);
    }

    @Transactional
    public TokenResponseDTO refreshToken(String refreshToken) {
        Usuario usuario = usuarioRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new RegraDeNegocioException("Refresh token inválido"));

        if (usuario.getRefreshTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RegraDeNegocioException("Refresh token expirado. Por favor, faça login novamente.");
        }

        return generateTokenResponse(usuario);
    }

    @Transactional
    public void forgotPassword(ForgotPasswordDTO request) {
        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado"));

        String token = UUID.randomUUID().toString();
        usuario.setPasswordResetToken(token);
        usuario.setPasswordResetExpiry(LocalDateTime.now().plusMinutes(15));
        usuarioRepository.save(usuario);

        log.info("MOCK EMAIL: Link de recuperação para {}: https://meusistema.com/reset-password?token={}", 
                usuario.getEmail(), token);
    }

    @Transactional
    public void resetPassword(ResetPasswordDTO request) {
        Usuario usuario = usuarioRepository.findByPasswordResetToken(request.token())
                .orElseThrow(() -> new RegraDeNegocioException("Token de recuperação inválido"));

        if (usuario.getPasswordResetExpiry().isBefore(LocalDateTime.now())) {
            throw new RegraDeNegocioException("Token de recuperação expirado");
        }

        usuario.setSenha(passwordEncoder.encode(request.novaSenha()));
        usuario.setPasswordResetToken(null);
        usuario.setPasswordResetExpiry(null);
        usuarioRepository.save(usuario);
    }

    private TokenResponseDTO generateTokenResponse(Usuario usuario) {
        String accessToken = tokenProvider.generateAccessToken(usuario);
        String refreshToken = UUID.randomUUID().toString();

        usuario.setRefreshToken(refreshToken);
        usuario.setRefreshTokenExpiry(LocalDateTime.now().plusDays(1));
        usuarioRepository.save(usuario);

        return new TokenResponseDTO(accessToken, refreshToken, false);
    }
}

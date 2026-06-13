//package br.com.aliancapsicossocial.services;
//
//import br.com.aliancapsicossocial.enums.usuario.UsuarioRole;
//import br.com.aliancapsicossocial.models.Usuario;
//import br.com.aliancapsicossocial.repositories.UsuarioRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class AutenticacaoServiceTest {
//
//    @Mock
//    private UsuarioRepository repository;
//
//    @InjectMocks
//    private AutenticacaoService service;
//
//    @Test
//    void deveRetornarUsuarioQuandoEmailExistir() {
//        // Given (Arrange) - Cenário, mocks e inputs
//        var usuario = Usuario.builder()
//                .email("paciente@example.com")
//                .senha("senha-criptografada")
//                .role(UsuarioRole.PACIENTE)
//                .build();
//        when(repository.findByEmail("paciente@example.com")).thenReturn(Optional.of(usuario));
//
//        // When (Act) - Ação sendo testada
//        var resultado = service.loadUserByUsername("paciente@example.com");
//
//        // Then (Assert) - Validação dos resultados
//        assertThat(resultado).isSameAs(usuario);
//        verify(repository).findByEmail("paciente@example.com");
//    }
//
//    @Test
//    void deveLancarExcecaoQuandoEmailNaoExistir() {
//        // Given (Arrange) - Cenário, mocks e inputs
//        when(repository.findByEmail("ausente@example.com")).thenReturn(Optional.empty());
//
//        // When (Act) - Ação sendo testada
//        var resultado = assertThatThrownBy(() -> service.loadUserByUsername("ausente@example.com"));
//
//        // Then (Assert) - Validação dos resultados
//        resultado.isInstanceOf(UsernameNotFoundException.class)
//                .hasMessage("UsuÃ¡rio nÃ£o encontrado com o e-mail informado.");
//        verify(repository).findByEmail("ausente@example.com");
//    }
//}

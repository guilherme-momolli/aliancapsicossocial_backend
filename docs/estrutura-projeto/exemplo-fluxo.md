# Exemplo de Fluxo: Service, Helper e Validator

Este exemplo demonstra como as camadas interagem para manter o Service limpo e focado no fluxo de negocio.

## 1. Validator (Validacao de Variavel)

Focado em uma regra unica e reutilizavel.

```java
@Component
public class EmailValidator {
    public void validar(String email) {
        if (email == null || !email.contains("@")) {
            throw new RegraDeNegocioException("Formato de email invalido.");
        }
    }
}
```

## 2. Helper (Validacao de Classe/Dominio)

Agrupa as validacoes necessarias para uma entidade ou acao especifica.

```java
@Component
@RequiredArgsConstructor
public class UsuarioHelper {
    private final EmailValidator emailValidator;
    private final UsuarioRepository repository;

    public void validarCriacao(Usuario usuario) {
        // Usa o validator para uma variavel especifica
        emailValidator.validar(usuario.getEmail());

        // Realiza uma validacao de dominio que depende de persistencia
        if (repository.existsByEmail(usuario.getEmail())) {
            throw new RegraDeNegocioException("Email ja cadastrado.");
        }
    }
}
```

## 3. Service (Orquestracao)

Mantem-se limpo, delegando a complexidade da validacao.

```java
@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioHelper helper;
    private final UsuarioRepository repository;

    @Transactional
    public Usuario criar(Usuario usuario) {
        // Delega a validacao complexa para o helper
        helper.validarCriacao(usuario);

        // Prossegue com o fluxo de negocio
        return repository.save(usuario);
    }
}
```

## Beneficios deste Padrao

1. **Testabilidade**: Cada camada pode ser testada unitariamente com facilidade.
2. **Reutilizacao**: O `EmailValidator` pode ser usado em qualquer lugar do sistema.
3. **Leitura**: O `UsuarioService` torna-se uma leitura de alto nivel do processo de negocio.
4. **Manutenibilidade**: Mudancas em regras de email afetam apenas o `EmailValidator`.

# 🔑 Boas Práticas e Padrões para JWT (JSON Web Tokens)

Este documento estabelece as diretrizes e boas práticas para implementação e uso de **JWT** na API do **Aliança Psicossocial** utilizando a biblioteca **Auth0 java-jwt**.

---

## 🔒 1. Princípios de Segurança e Geração de Tokens

* **Segredo da Assinatura (Secret Key):**
  * > [!CAUTION]
    > **Nunca escreva a chave secreta diretamente no código ou em arquivos versionados.**
    > Use uma chave forte (mínimo de 256 bits/32 bytes) gerada aleatoriamente e carregada a partir de variáveis de ambiente.
    * Em desenvolvimento, use um valor padrão seguro em `application.properties`:
      ```properties
      api.security.token.secret=${JWT_SECRET:segredo-super-secreto-e-longo-para-desenvolvimento-123456}
      ```
    * Em produção/homologação, a variável `JWT_SECRET` deve ser injetada via Docker Compose ou AWS Secrets Manager.
* **Algoritmo de Assinatura:**
  * Utilize sempre algoritmos seguros, como o **HMAC256** (`Algorithm.HMAC256(secret)`).
* **Tempo de Expiração (TTL):**
  * Mantenha o tempo de vida do token de acesso curto (ex: **2 horas** para a API em geral).
  * Defina a expiração programaticamente no momento da geração do token:
    ```java
    Instant expiracao = LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    ```

---

## 🛠️ 2. Implementação do `TokenService`

Crie uma classe dedicada na camada `services/` ou `configurations/` para encapsular a lógica de criação e validação do token JWT.

```java
@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    private static final String ISSUER = "aliancapsicossocial-api";

    public String gerarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(usuario.getEmail())
                    .withClaim("role", usuario.getRole().name())
                    .withExpiresAt(gerarDataExpiracao())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    public String validarToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            // Retorna null ou lança uma exceção customizada se o token for inválido/expirado
            return null;
        }
    }

    private Instant gerarDataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
```

---

## 🛡️ 3. Integração com o Spring Security (Stateless)

* **Filtro de Segurança Personalizado (`SecurityFilter`):**
  * Crie um filtro interceptor (herdando de `OncePerRequestFilter`) que seja executado antes do filtro padrão do Spring Security (`UsernamePasswordAuthenticationFilter`).
  * O filtro deve:
    1. Recuperar o token do cabeçalho `Authorization` (formato `Bearer <token>`).
    2. Validar o token usando o `TokenService`.
    3. Se válido, buscar o usuário no banco de dados e autenticá-lo no contexto de segurança do Spring.

```java
@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuarioRepository repository;

    public SecurityFilter(TokenService tokenService, UsuarioRepository repository) {
        this.tokenService = tokenService;
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = recuperarToken(request);
        if (token != null) {
            String email = tokenService.validarToken(token);
            if (email != null) {
                UserDetails usuario = repository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
                
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
}
```

* **Política de Sessão Stateless:**
  * Configure o `SecurityFilterChain` para desabilitar a criação de sessões HTTP no servidor (sessões Stateful baseadas em Cookies).
  ```java
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, SecurityFilter securityFilter) throws Exception {
      return http
          .csrf(csrf -> csrf.disable())
          .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
          .authorizeHttpRequests(authorize -> authorize
              .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
              .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
              .anyRequest().authenticated()
          )
          .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
          .build();
  }
  ```

---

## 🚫 4. O que EVITAR no JWT

* > [!WARNING]
  > **Dados Sensíveis nos Claims:** Não insira informações altamente confidenciais no payload do JWT (como senhas, documentos pessoais sensíveis como CPF ou dados financeiros). O payload é apenas codificado em Base64 e pode ser facilmente lido por qualquer um que intercepte o token.
  >
  > **Chaves Fracas:** Chaves curtas ou óbvias (como `123456` ou `minha-chave`) são vulneráveis a ataques de força bruta offline. Use chaves geradas por geradores de senhas seguros (mínimo de 32 caracteres).

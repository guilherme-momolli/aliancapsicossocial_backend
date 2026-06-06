# 🏷️ Guia de Uso do Lombok com Entidades JPA

Este guia detalha o uso correto das anotações da biblioteca **Lombok** em conjunto com as entidades do **JPA/Hibernate** para evitar problemas comuns de performance e bugs silenciosos.

---

## ⚠️ O Perigo do `@Data` em Entidades JPA

A anotação `@Data` do Lombok gera automaticamente `@Getter`, `@Setter`, `@RequiredArgsConstructor`, `@ToString`, `@EqualsAndHashCode`.

> [!WARNING]
> **Nunca use `@Data` em Entidades JPA.**
> O mapeamento do `@ToString` e `@EqualsAndHashCode` automáticos utiliza todos os campos da classe. Quando você tem relacionamentos bidirecionais (como `@OneToMany` ou `@ManyToMany`), isso gera chamadas recursivas infinitas entre as duas entidades associadas, resultando em um erro fatal de **StackOverflowError** ou em carregamentos preguiçosos desnecessários (**LazyInitializationException**).

---

## 🎯 Abordagem Recomendada para Entidades

Para obter os benefícios do Lombok de forma segura em entidades do banco de dados, declare as anotações individualmente:

```java
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    // Sobrescreva o equals e hashCode considerando apenas o ID da entidade
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario usuario)) return false;
        return id != null && id.equals(usuario.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
```

### Explicação do `equals` e `hashCode` manual:
* **Equals:** Duas instâncias de entidade representam o mesmo registro no banco se tiverem o mesmo ID.
* **HashCode:** Retornamos sempre um valor fixo (o hash code do próprio tipo da classe) para garantir que o hash code da entidade seja estável, independentemente de a entidade estar salva (com ID persistido) ou em estado transiente (sem ID ainda). Isso previne bugs em coleções do Java (como `HashSet` ou `HashMap`).

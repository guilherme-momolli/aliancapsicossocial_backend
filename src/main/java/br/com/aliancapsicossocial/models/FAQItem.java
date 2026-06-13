package br.com.aliancapsicossocial.models;

import br.com.aliancapsicossocial.enums.faq.FAQCategory;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.envers.Audited;

import java.util.UUID;

@Audited
@Entity
@Table(name = "faq_items")
@SQLDelete(sql = "UPDATE faq_items SET ativo = false WHERE id = ?")
@SQLRestriction("ativo = true")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FAQItem extends AuditableBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String pergunta;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String resposta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FAQCategory categoria;

    @Column(nullable = false)
    private Integer ordem;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FAQItem that)) return false;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

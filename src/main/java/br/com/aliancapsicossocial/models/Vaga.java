package br.com.aliancapsicossocial.models;

import br.com.aliancapsicossocial.enums.ats.VagaStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

import java.time.LocalDate;
import java.util.UUID;

@Audited
@Entity
@Table(name = "vagas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vaga extends AuditableBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Column(nullable = false)
    private String requisitos;

    @Column(name = "data_limite")
    private LocalDate dataLimite;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private VagaStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vaga vaga)) return false;
        return id != null && id.equals(vaga.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

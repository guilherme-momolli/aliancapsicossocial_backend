package br.com.aliancapsicossocial.models;

import br.com.aliancapsicossocial.enums.ats.CandidaturaStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;
import java.util.UUID;

@Audited
@Entity
@Table(name = "candidaturas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Candidatura extends AuditableBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vaga_id", nullable = false)
    private Vaga vaga;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pessoa_id", nullable = false)
    private Pessoa pessoa;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private CandidaturaStatus status;

    @Column(name = "data_candidatura", nullable = false)
    private LocalDateTime dataCandidatura;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Candidatura that)) return false;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

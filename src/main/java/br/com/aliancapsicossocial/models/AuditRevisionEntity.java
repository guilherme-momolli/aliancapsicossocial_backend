package br.com.aliancapsicossocial.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

@Getter
@Setter
@Entity
@RevisionEntity(AuditRevisionListener.class)
@Table(name = "sys_revision_info")
public class AuditRevisionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @RevisionNumber
    @Column(name = "revision_id", nullable = false)
    private int id;

    @RevisionTimestamp
    @Column(name = "revision_timestamp", nullable = false)
    private long timestamp;

    @Column(name = "username", nullable = false, length = 255)
    private String username;
}

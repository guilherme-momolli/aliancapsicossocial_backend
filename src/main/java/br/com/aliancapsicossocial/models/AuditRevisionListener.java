package br.com.aliancapsicossocial.models;

import br.com.aliancapsicossocial.configurations.AuditorAwareImpl;
import org.hibernate.envers.RevisionListener;

public class AuditRevisionListener implements RevisionListener {

    private final AuditorAwareImpl auditorAware = new AuditorAwareImpl();

    @Override
    public void newRevision(Object revisionEntity) {
        AuditRevisionEntity auditRevisionEntity = (AuditRevisionEntity) revisionEntity;
        auditRevisionEntity.setUsername(auditorAware.getCurrentAuditor().orElse("system"));
    }
}

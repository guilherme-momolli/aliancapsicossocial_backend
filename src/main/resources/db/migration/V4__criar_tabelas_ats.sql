CREATE TABLE vagas (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT NOT NULL,
    requisitos TEXT NOT NULL,
    data_limite DATE,
    status VARCHAR(50) NOT NULL,
    criado_por VARCHAR(255) NOT NULL,
    data_criacao TIMESTAMP NOT NULL,
    atualizado_por VARCHAR(255) NOT NULL,
    data_atualizacao TIMESTAMP NOT NULL
);

CREATE TABLE candidaturas (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    vaga_id UUID NOT NULL,
    pessoa_id UUID NOT NULL,
    status VARCHAR(50) NOT NULL,
    data_candidatura TIMESTAMP NOT NULL,
    observacoes TEXT,
    criado_por VARCHAR(255) NOT NULL,
    data_criacao TIMESTAMP NOT NULL,
    atualizado_por VARCHAR(255) NOT NULL,
    data_atualizacao TIMESTAMP NOT NULL,
    CONSTRAINT fk_candidatura_vaga FOREIGN KEY (vaga_id) REFERENCES vagas (id),
    CONSTRAINT fk_candidatura_pessoa FOREIGN KEY (pessoa_id) REFERENCES pessoas (id)
);

-- Auditing tables (Envers)
CREATE TABLE vagas_aud (
    id UUID NOT NULL,
    revision_id INTEGER NOT NULL,
    revision_type SMALLINT,
    titulo VARCHAR(255),
    descricao TEXT,
    requisitos TEXT,
    data_limite DATE,
    status VARCHAR(50),
    CONSTRAINT pk_vagas_aud PRIMARY KEY (id, revision_id),
    CONSTRAINT fk_vagas_aud_revision FOREIGN KEY (revision_id) REFERENCES sys_revision_info (revision_id)
);

CREATE TABLE candidaturas_aud (
    id UUID NOT NULL,
    revision_id INTEGER NOT NULL,
    revision_type SMALLINT,
    vaga_id UUID,
    pessoa_id UUID,
    status VARCHAR(50),
    data_candidatura TIMESTAMP,
    observacoes TEXT,
    CONSTRAINT pk_candidaturas_aud PRIMARY KEY (id, revision_id),
    CONSTRAINT fk_candidaturas_aud_revision FOREIGN KEY (revision_id) REFERENCES sys_revision_info (revision_id)
);

-- Indexes for Dashboard performance
CREATE INDEX idx_vagas_status ON vagas(status);
CREATE INDEX idx_candidaturas_status ON candidaturas(status);
CREATE INDEX idx_candidaturas_vaga ON candidaturas(vaga_id);
CREATE INDEX idx_candidaturas_data ON candidaturas(data_candidatura);

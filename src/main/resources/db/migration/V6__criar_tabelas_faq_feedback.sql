-- Criar tabelas para FAQ e Feedback
CREATE TABLE faq_items (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    pergunta VARCHAR(255) NOT NULL,
    resposta TEXT NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    ordem INTEGER NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    criado_por VARCHAR(255) NOT NULL,
    data_criacao TIMESTAMP NOT NULL,
    atualizado_por VARCHAR(255) NOT NULL,
    data_atualizacao TIMESTAMP NOT NULL
);

CREATE TABLE feedbacks (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    usuario_id UUID NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    assunto VARCHAR(255) NOT NULL,
    mensagem TEXT NOT NULL,
    nota INTEGER NOT NULL,
    status VARCHAR(50) NOT NULL,
    resposta_suporte TEXT,
    respondido_por_id UUID,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    criado_por VARCHAR(255) NOT NULL,
    data_criacao TIMESTAMP NOT NULL,
    atualizado_por VARCHAR(255) NOT NULL,
    data_atualizacao TIMESTAMP NOT NULL,
    CONSTRAINT fk_feedback_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios (id),
    CONSTRAINT fk_feedback_respondido_por FOREIGN KEY (respondido_por_id) REFERENCES usuarios (id)
);

-- Tabelas de Auditoria (Envers)
CREATE TABLE faq_items_aud (
    id UUID NOT NULL,
    revision_id INTEGER NOT NULL,
    revision_type SMALLINT,
    pergunta VARCHAR(255),
    resposta TEXT,
    categoria VARCHAR(50),
    ordem INTEGER,
    ativo BOOLEAN,
    CONSTRAINT pk_faq_items_aud PRIMARY KEY (id, revision_id),
    CONSTRAINT fk_faq_items_aud_revision FOREIGN KEY (revision_id) REFERENCES sys_revision_info (revision_id)
);

CREATE TABLE feedbacks_aud (
    id UUID NOT NULL,
    revision_id INTEGER NOT NULL,
    revision_type SMALLINT,
    usuario_id UUID,
    tipo VARCHAR(50),
    assunto VARCHAR(255),
    mensagem TEXT,
    nota INTEGER,
    status VARCHAR(50),
    resposta_suporte TEXT,
    respondido_por_id UUID,
    ativo BOOLEAN,
    CONSTRAINT pk_feedbacks_aud PRIMARY KEY (id, revision_id),
    CONSTRAINT fk_feedbacks_aud_revision FOREIGN KEY (revision_id) REFERENCES sys_revision_info (revision_id)
);

-- Índices
CREATE INDEX idx_faq_items_categoria ON faq_items (categoria);
CREATE INDEX idx_feedbacks_usuario_id ON feedbacks (usuario_id);
CREATE INDEX idx_feedbacks_status ON feedbacks (status);
CREATE INDEX idx_faq_items_aud_revision_id ON faq_items_aud (revision_id);
CREATE INDEX idx_feedbacks_aud_revision_id ON feedbacks_aud (revision_id);

CREATE TABLE enderecos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    logradouro VARCHAR(255) NOT NULL,
    cep VARCHAR(9) NOT NULL,
    bairro VARCHAR(255) NOT NULL,
    municipio VARCHAR(255) NOT NULL,
    estado VARCHAR(255) NOT NULL,
    pais VARCHAR(255) NOT NULL,
    criado_por VARCHAR(255) NOT NULL,
    data_criacao TIMESTAMP NOT NULL,
    atualizado_por VARCHAR(255) NOT NULL,
    data_atualizacao TIMESTAMP NOT NULL
);

CREATE TABLE pessoas (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    foto_perfil VARCHAR(255),
    nome VARCHAR(255) NOT NULL,
    data_nascimento DATE NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    rg VARCHAR(20),
    usuario_id UUID UNIQUE,
    endereco_id UUID UNIQUE,
    criado_por VARCHAR(255) NOT NULL,
    data_criacao TIMESTAMP NOT NULL,
    atualizado_por VARCHAR(255) NOT NULL,
    data_atualizacao TIMESTAMP NOT NULL,
    CONSTRAINT fk_pessoa_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios (id),
    CONSTRAINT fk_pessoa_endereco FOREIGN KEY (endereco_id) REFERENCES enderecos (id)
);

-- Auditing tables (Envers)
CREATE TABLE usuarios_aud (
    id UUID NOT NULL,
    revision_id INTEGER NOT NULL,
    revision_type SMALLINT,
    email VARCHAR(255),
    senha VARCHAR(255),
    role VARCHAR(50),
    CONSTRAINT pk_usuarios_aud PRIMARY KEY (id, revision_id),
    CONSTRAINT fk_usuarios_aud_revision FOREIGN KEY (revision_id) REFERENCES sys_revision_info (revision_id)
);

CREATE TABLE enderecos_aud (
    id UUID NOT NULL,
    revision_id INTEGER NOT NULL,
    revision_type SMALLINT,
    logradouro VARCHAR(255),
    cep VARCHAR(9),
    bairro VARCHAR(255),
    municipio VARCHAR(255),
    estado VARCHAR(255),
    pais VARCHAR(255),
    CONSTRAINT pk_enderecos_aud PRIMARY KEY (id, revision_id),
    CONSTRAINT fk_enderecos_aud_revision FOREIGN KEY (revision_id) REFERENCES sys_revision_info (revision_id)
);

CREATE TABLE pessoas_aud (
    id UUID NOT NULL,
    revision_id INTEGER NOT NULL,
    revision_type SMALLINT,
    foto_perfil VARCHAR(255),
    nome VARCHAR(255),
    data_nascimento DATE,
    cpf VARCHAR(14),
    rg VARCHAR(20),
    usuario_id UUID,
    endereco_id UUID,
    CONSTRAINT pk_pessoas_aud PRIMARY KEY (id, revision_id),
    CONSTRAINT fk_pessoas_aud_revision FOREIGN KEY (revision_id) REFERENCES sys_revision_info (revision_id)
);

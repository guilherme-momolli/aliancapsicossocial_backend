-- Adicionar coluna ativo para Safe Delete em todas as tabelas principais
ALTER TABLE usuarios ADD COLUMN ativo BOOLEAN NOT NULL DEFAULT TRUE;
ALTER TABLE produtos ADD COLUMN ativo BOOLEAN NOT NULL DEFAULT TRUE;
ALTER TABLE enderecos ADD COLUMN ativo BOOLEAN NOT NULL DEFAULT TRUE;
ALTER TABLE pessoas ADD COLUMN ativo BOOLEAN NOT NULL DEFAULT TRUE;
ALTER TABLE vagas ADD COLUMN ativo BOOLEAN NOT NULL DEFAULT TRUE;
ALTER TABLE candidaturas ADD COLUMN ativo BOOLEAN NOT NULL DEFAULT TRUE;

-- Adicionar coluna ativo nas tabelas de auditoria do Envers
ALTER TABLE usuarios_aud ADD COLUMN ativo BOOLEAN;
ALTER TABLE produtos_aud ADD COLUMN ativo BOOLEAN;
ALTER TABLE enderecos_aud ADD COLUMN ativo BOOLEAN;
ALTER TABLE pessoas_aud ADD COLUMN ativo BOOLEAN;
ALTER TABLE vagas_aud ADD COLUMN ativo BOOLEAN;
ALTER TABLE candidaturas_aud ADD COLUMN ativo BOOLEAN;

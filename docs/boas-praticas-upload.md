# 📂 Boas Práticas: Upload de Arquivos e Gestão de Documentos

Este documento estabelece os padrões técnicos e arquiteturais para o upload, armazenamento e recuperação de arquivos no sistema **Aliança Psicossocial**.

---

## 🏗️ Estratégia de Armazenamento

1.  **Storage Externo (AWS S3):** Nunca armazene binários (blobs) diretamente no banco de dados relacional. Utilize o Amazon S3 para escalabilidade e performance.
2.  **Referência por Link:** O banco de dados deve armazenar apenas a `Key` (caminho relativo) ou a `URL` final do arquivo.
3.  **Imutabilidade:** Arquivos uma vez enviados não devem ser editados. Se houver alteração, um novo arquivo deve ser enviado e a referência no banco atualizada.

---

## 📁 Organização de Pastas (Bucket Structure)

A estrutura de pastas no S3 deve ser lógica e hierárquica para facilitar a auditoria e limpeza:

```text
bucket-name/
├── candidatos/
│   └── {candidato_id}/
│       ├── documentos/ (RG, CPF)
│       ├── curriculos/
│       └── fotos/
├── empregadores/
│   └── {empregador_id}/
│       ├── logos/
│       └── contratos/
└── sistema/
    └── temporario/ (arquivos com TTL curto)
```

---

## 🛡️ Segurança e Validação

1.  **Limite de Tamanho:**
    *   Arquivos individuais: Máximo **10MB** (ajustável via config).
    *   Total por requisição (Batch): Máximo **50MB**.
2.  **Sanitização de Nomes:** Remova caracteres especiais e espaços dos nomes dos arquivos antes do upload. Adicione um prefixo de timestamp ou UUID para evitar colisões.
3.  **Content-Type:** Valide o `Content-Type` no backend para evitar execução de scripts maliciosos.
4.  **Acesso Privado:** Por padrão, os objetos no S3 devem ser **privados**. O acesso deve ser feito via **Presigned URLs** com tempo de expiração curto (ex: 15-30 minutos).

---

## 🔗 Persistência de Links no Banco de Dados

Ao salvar a referência do arquivo no banco de dados, prefira armazenar a **Key** (caminho relativo) em vez da URL completa.

- **Por que salvar a Key?** Se o bucket mudar de nome ou região, você só precisa atualizar a URL base na aplicação, sem precisar rodar um script de replace em milhões de linhas do banco de dados.
- **Exemplo de Tabela:**
  ```sql
  CREATE TABLE anexos (
      id UUID PRIMARY KEY,
      origem_id UUID, -- FK para Candidato, Vaga, etc
      nome_arquivo VARCHAR(255),
      s3_key VARCHAR(500), -- Ex: 'candidatos/123/cv_joao.pdf'
      content_type VARCHAR(100)
  );
  ```

---

## 💻 Implementação Técnica (Spring Boot)

### Configuração de Limites (`application.yml`)
```yaml
spring:
  servlet:
    multipart:
      max-file-size: 10MB
      max-request-size: 50MB
```

### Padrão de Retorno (DTO)
Sempre retorne um objeto estruturado após o upload, facilitando o mapeamento no frontend:

```json
{
  "nomeOriginal": "curriculo.pdf",
  "nomeArmazenamento": "1623456789_curriculo.pdf",
  "url": "https://s3.sa-east-1.amazonaws.com/...",
  "tamanho": 1048576,
  "contentType": "application/pdf"
}
```

---

## ⚠️ O que EVITAR (Anti-Patterns)

- **NÃO** use o nome original do arquivo como chave única no S3.
- **NÃO** exponha credenciais da AWS no frontend.
- **NÃO** permita uploads sem autenticação prévia.
- **NÃO** salve caminhos absolutos locais (ex: `C:\uploads\`) no banco de dados.

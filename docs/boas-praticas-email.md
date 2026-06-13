#  Boas Práticas: Comunicação por E-mail

Este documento estabelece os padrões para o envio de e-mails no sistema **Aliança Psicossocial**.

---

##  Estratégia de Envio

1.  **Assincronismo:** O envio de e-mail nunca deve bloquear a requisição do usuário. Utilize `@Async` ou filas (SQS/RabbitMQ) para processar o envio em background.
2.  **Provedor (AWS SES):** Em produção, utilize o Amazon SES para alta entregabilidade. Localmente, utilize o Mailtrap ou SMTP do Gmail para testes.
3.  **Templates (Thymeleaf):** Nunca concatene strings HTML no código Java. Utilize o Thymeleaf para separar a lógica da apresentação.

---

##  Design e Conteúdo

1.  **HTML/CSS Inline:** A maioria dos clientes de e-mail (Outlook, Gmail) ignora classes CSS externas. Utilize CSS inline ou ferramentas de processamento que convertem classes em estilos inline.
2.  **Responsividade:** Utilize tabelas (infelizmente necessárias) e layouts fluídos para garantir a leitura em dispositivos móveis.
3.  **Fallbacks:** Sempre inclua uma versão em texto simples (`plain/text`) além do HTML.
4.  **JavaScript:** **NÃO utilize JavaScript.** Quase todos os clientes de e-mail bloqueiam scripts por segurança. Interatividade deve ser limitada a links e botões (âncoras HTML).

---

## ️ Segurança e LGPD

1.  **Opt-out:** Todo e-mail de marketing ou notificação deve conter um link de "Descadastrar".
2.  **Identificação:** O rodapé deve conter informações da empresa (CNPJ, Endereço) conforme exigido por leis internacionais (CAN-SPAM Act).
3.  **Segredos:** Nunca envie senhas em texto puro. Envie links de expiração curta para reset de senha.

---

##  Estrutura de Pastas

```text
src/main/resources/templates/mail/
├── layouts/
│   └── main-layout.html (Base com Header/Footer)
├── welcome-email.html
├── password-reset.html
└── notification.html
```

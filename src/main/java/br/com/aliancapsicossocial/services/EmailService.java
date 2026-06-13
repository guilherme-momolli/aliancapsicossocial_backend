//package br.com.aliancapsicossocial.services;
//
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//import org.thymeleaf.TemplateEngine;
//import org.thymeleaf.context.Context;
//
//import java.util.Map;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class EmailService {
//
//    private final JavaMailSender mailSender;
//    private final TemplateEngine templateEngine;
//
//    @Value("${spring.mail.username:noreply@aliancapsicossocial.com.br}")
//    private String fromEmail;
//
//    @Async
//    public void sendHtmlEmail(String to, String subject, String templateName, Map<String, Object> variables) {
//        try {
//            Context context = new Context();
//            context.setVariables(variables);
//
//            String htmlContent = templateEngine.process("mail/" + templateName, context);
//
//            MimeMessage message = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//
//            helper.setFrom(fromEmail);
//            helper.setTo(to);
//            helper.setSubject(subject);
//            helper.setText(htmlContent, true);
//
//            mailSender.send(message);
//            log.info("E-mail enviado com sucesso para: {}", to);
//
//        } catch (MessagingException e) {
//            log.error("Erro ao enviar e-mail para {}: {}", to, e.getMessage());
//            throw new RuntimeException("Falha ao enviar e-mail", e);
//        }
//    }
//}

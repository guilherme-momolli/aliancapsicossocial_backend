package br.com.aliancapsicossocial.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.Body;
import software.amazon.awssdk.services.ses.model.Content;
import software.amazon.awssdk.services.ses.model.Destination;
import software.amazon.awssdk.services.ses.model.Message;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;

@Service
public class AwsEmailService {

    private final SesClient sesClient;
    private final String fromEmail;

    public AwsEmailService(SesClient sesClient, 
                           @Value("${aws.ses.from}") String fromEmail) {
        this.sesClient = sesClient;
        this.fromEmail = fromEmail;
    }

    @Async
    public void enviarEmail(String destinatario, String assunto, String corpoHtml) {
        Destination destination = Destination.builder()
                .toAddresses(destinatario)
                .build();

        Content subjectContent = Content.builder().data(assunto).charset("UTF-8").build();
        Content htmlContent = Content.builder().data(corpoHtml).charset("UTF-8").build();

        Body body = Body.builder()
                .html(htmlContent)
                .build();

        Message message = Message.builder()
                .subject(subjectContent)
                .body(body)
                .build();

        SendEmailRequest request = SendEmailRequest.builder()
                .source(fromEmail)
                .destination(destination)
                .message(message)
                .build();

        try {
            sesClient.sendEmail(request);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao enviar e-mail via Amazon SES", e);
        }
    }
}

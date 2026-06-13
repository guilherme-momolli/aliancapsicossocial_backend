//package br.com.aliancapsicossocial.services;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import software.amazon.awssdk.services.ses.SesClient;
//import software.amazon.awssdk.services.ses.model.SendEmailRequest;
//import software.amazon.awssdk.services.ses.model.SendEmailResponse;
//import software.amazon.awssdk.services.ses.model.SesException;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class AwsEmailServiceTest {
//
//    @Mock
//    private SesClient sesClient;
//
//    @Test
//    void deveEnviarEmailComOrigemDestinoAssuntoECorpoHtml() {
//        // Given (Arrange) - Cenário, mocks e inputs
//        var service = new AwsEmailService(sesClient, "no-reply@example.com");
//        when(sesClient.sendEmail(org.mockito.ArgumentMatchers.any(SendEmailRequest.class)))
//                .thenReturn(SendEmailResponse.builder().messageId("message-id").build());
//        var requestCaptor = ArgumentCaptor.forClass(SendEmailRequest.class);
//
//        // When (Act) - Ação sendo testada
//        service.enviarEmail("destino@example.com", "Assunto", "<p>Corpo</p>");
//
//        // Then (Assert) - Validação dos resultados
//        verify(sesClient).sendEmail(requestCaptor.capture());
//        var request = requestCaptor.getValue();
//        assertThat(request.source()).isEqualTo("no-reply@example.com");
//        assertThat(request.destination().toAddresses()).containsExactly("destino@example.com");
//        assertThat(request.message().subject().data()).isEqualTo("Assunto");
//        assertThat(request.message().body().html().data()).isEqualTo("<p>Corpo</p>");
//    }
//
//    @Test
//    void deveEncapsularFalhaDoSesEmRuntimeException() {
//        // Given (Arrange) - Cenário, mocks e inputs
//        var service = new AwsEmailService(sesClient, "no-reply@example.com");
//        var falhaSes = SesException.builder().message("falha").build();
//        when(sesClient.sendEmail(org.mockito.ArgumentMatchers.any(SendEmailRequest.class))).thenThrow(falhaSes);
//
//        // When (Act) - Ação sendo testada
//        var resultado = assertThatThrownBy(
//                () -> service.enviarEmail("destino@example.com", "Assunto", "<p>Corpo</p>")
//        );
//
//        // Then (Assert) - Validação dos resultados
//        resultado.isInstanceOf(RuntimeException.class)
//                .hasMessage("Falha ao enviar e-mail via Amazon SES")
//                .hasCause(falhaSes);
//    }
//}

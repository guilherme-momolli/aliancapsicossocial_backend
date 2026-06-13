//package br.com.aliancapsicossocial.services;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.mock.web.MockMultipartFile;
//import software.amazon.awssdk.core.sync.RequestBody;
//import software.amazon.awssdk.services.s3.S3Client;
//import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
//import software.amazon.awssdk.services.s3.model.PutObjectRequest;
//import software.amazon.awssdk.services.s3.model.PutObjectResponse;
//import software.amazon.awssdk.services.s3.presigner.S3Presigner;
//import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
//import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
//
//import java.net.URL;
//import java.time.Duration;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class S3StorageServiceTest {
//
//    @Mock
//    private S3Client s3Client;
//
//    @Mock
//    private S3Presigner s3Presigner;
//
//    @Test
//    void deveEnviarMultipartFileParaBucketComKeyEContentType() throws Exception {
//        // Given (Arrange) - Cenário, mocks e inputs
//        var service = new S3StorageService(s3Client, s3Presigner, "bucket-teste");
//        var file = new MockMultipartFile("file", "arquivo teste.txt", "text/plain", "conteudo".getBytes());
//        when(s3Client.putObject(any(PutObjectRequest.class), any(RequestBody.class)))
//                .thenReturn(PutObjectResponse.builder().build());
//        var requestCaptor = ArgumentCaptor.forClass(PutObjectRequest.class);
//
//        // When (Act) - Ação sendo testada
//        String key = service.uploadFile("documentos", file);
//
//        // Then (Assert) - Validação dos resultados
//        verify(s3Client).putObject(requestCaptor.capture(), any(RequestBody.class));
//        var request = requestCaptor.getValue();
//        assertThat(request.bucket()).isEqualTo("bucket-teste");
//        assertThat(request.key()).contains("documentos/");
//        assertThat(request.key()).contains("arquivo_teste.txt"); // Sanitizado
//        assertThat(request.contentType()).isEqualTo("text/plain");
//        assertThat(key).isEqualTo(request.key());
//    }
//
//    @Test
//    void deveEnviarBytesParaBucketComKeyEContentType() {
//        // Given (Arrange) - Cenário, mocks e inputs
//        var service = new S3StorageService(s3Client, s3Presigner, "bucket-teste");
//        when(s3Client.putObject(any(PutObjectRequest.class), any(RequestBody.class)))
//                .thenReturn(PutObjectResponse.builder().build());
//        var requestCaptor = ArgumentCaptor.forClass(PutObjectRequest.class);
//
//        // When (Act) - Ação sendo testada
//        service.uploadFile("imagens/avatar.png", new byte[] {1, 2, 3}, "image/png");
//
//        // Then (Assert) - Validação dos resultados
//        verify(s3Client).putObject(requestCaptor.capture(), any(RequestBody.class));
//        var request = requestCaptor.getValue();
//        assertThat(request.bucket()).isEqualTo("bucket-teste");
//        assertThat(request.key()).isEqualTo("imagens/avatar.png");
//        assertThat(request.contentType()).isEqualTo("image/png");
//    }
//
//    @Test
//    void deveExcluirArquivoDoBucketComKeyInformada() {
//        // Given (Arrange) - Cenário, mocks e inputs
//        var service = new S3StorageService(s3Client, s3Presigner, "bucket-teste");
//        var requestCaptor = ArgumentCaptor.forClass(DeleteObjectRequest.class);
//
//        // When (Act) - Ação sendo testada
//        service.deleteFile("documentos/arquivo.txt");
//
//        // Then (Assert) - Validação dos resultados
//        verify(s3Client).deleteObject(requestCaptor.capture());
//        var request = requestCaptor.getValue();
//        assertThat(request.bucket()).isEqualTo("bucket-teste");
//        assertThat(request.key()).isEqualTo("documentos/arquivo.txt");
//    }
//
//    @Test
//    void deveGerarUrlPreAssinadaParaBucketKeyEExpiracao() throws Exception {
//        // Given (Arrange) - Cenário, mocks e inputs
//        var service = new S3StorageService(s3Client, s3Presigner, "bucket-teste");
//        var presignedRequest = PresignedGetObjectRequest.builder()
//                .isBrowserExecutable(true)
//                .url(new URL("https://bucket-teste.s3.amazonaws.com/documentos/arquivo.txt"))
//                .build();
//        when(s3Presigner.presignGetObject(any(GetObjectPresignRequest.class))).thenReturn(presignedRequest);
//        var requestCaptor = ArgumentCaptor.forClass(GetObjectPresignRequest.class);
//
//        // When (Act) - Ação sendo testada
//        var resultado = service.generatePresignedUrl("documentos/arquivo.txt", Duration.ofMinutes(10));
//
//        // Then (Assert) - Validação dos resultados
//        verify(s3Presigner).presignGetObject(requestCaptor.capture());
//        assertThat(resultado).isEqualTo("https://bucket-teste.s3.amazonaws.com/documentos/arquivo.txt");
//        assertThat(requestCaptor.getValue().signatureDuration()).isEqualTo(Duration.ofMinutes(10));
//    }
//}

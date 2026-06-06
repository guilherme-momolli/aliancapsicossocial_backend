package br.com.aliancapsicossocial.helpers;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ecr.EcrClient;
import software.amazon.awssdk.services.ecr.model.AuthorizationData;
import software.amazon.awssdk.services.ecr.model.GetAuthorizationTokenResponse;

import java.util.Base64;

public class EcrLoginHelper {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("Erro: Credenciais insuficientes.");
            System.err.println("Uso: java EcrLoginHelper <accessKey> <secretKey> <region>");
            System.exit(1);
        }

        String accessKey = args[0];
        String secretKey = args[1];
        String regionStr = args[2];

        try (EcrClient ecrClient = EcrClient.builder()
                .region(Region.of(regionStr))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                .build()) {

            GetAuthorizationTokenResponse response = ecrClient.getAuthorizationToken();
            AuthorizationData authData = response.authorizationData().get(0);
            
            // O token de autorização retornado é em Base64 no formato: AWS:senha_temporaria
            String decodedToken = new String(Base64.getDecoder().decode(authData.authorizationToken()));
            
            if (decodedToken.startsWith("AWS:")) {
                String password = decodedToken.substring(4);
                System.out.println(password);
            } else {
                System.err.println("Erro: Formato de token desconhecido.");
                System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("Erro ao obter token do ECR: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}

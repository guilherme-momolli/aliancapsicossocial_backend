# =========================================================================
# ESTÁGIO 1: Build da Aplicação (Maven + JDK 21)
# =========================================================================
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# Otimização de Cache: Copia apenas o pom.xml e baixa as dependências
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código-fonte e compila a aplicação (pulando os testes para agilizar o build)
COPY src ./src
RUN mvn clean package -DskipTests

# =========================================================================
# ESTÁGIO 2: Execução da Aplicação (JRE 21 Leve)
# =========================================================================
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Criação de um usuário não-root por motivos de segurança do container
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copia o JAR compilado do estágio de build
COPY --from=builder /app/target/*.jar app.jar

# Define as variáveis de ambiente padrão
ENV PORT=8080

# Expõe a porta que a aplicação rodará
EXPOSE ${PORT}

# Comando de inicialização usando o exec form do CMD
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]

# Use uma imagem base com o JDK
FROM eclipse-temurin:17-jdk-alpine

# Configura o diretório de trabalho
WORKDIR /app

# Copia o arquivo JAR para o container
COPY target/store-0.0.1-SNAPSHOT.jar app.jar

# Define o comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]

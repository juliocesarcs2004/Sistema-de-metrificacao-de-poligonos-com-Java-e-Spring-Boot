# Define a imagem base
FROM maven:3.8.1-openjdk-17-slim

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o arquivo pom.xml para o diretório de trabalho
COPY pom.xml .

# Baixa as dependências do Maven
RUN mvn dependency:go-offline -B

# Copia todo o código-fonte para o diretório de trabalho
COPY src .

# Compila o projeto usando o Maven
RUN mvn package

# Define a porta em que a aplicação será executada
EXPOSE 8080

# Define o comando para iniciar a aplicação quando o contêiner for iniciado
CMD ["java", "-jar", "target/poligonos-0.0.1-SNAPSHOT.jar"]
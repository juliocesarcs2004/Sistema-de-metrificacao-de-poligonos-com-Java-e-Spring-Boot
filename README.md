# Projeto Polígonos

Este é um projeto Java utilizando o framework Spring Boot para desenvolver uma aplicação de metrificação de polígonos. Ele utiliza o Maven como gerenciador de dependências e build.

## Configurações do projeto

### Requisitos

- Java 17 ou superior

### Configuração do ambiente de desenvolvimento

1. Clone o repositório do projeto:
   ```
   git clone https://github.com/seu-usuario/poligonos.git
   ```

2. Importe o projeto em sua IDE de preferência (ex: IntelliJ, Eclipse).

### Dependências

O arquivo `pom.xml` define as dependências do projeto. Aqui estão as principais dependências utilizadas:

- **spring-boot-starter-data-jpa**: fornece suporte para integração com o banco de dados e persistência de dados usando o JPA (Java Persistence API).
- **spring-boot-starter-validation**: oferece suporte para validação de dados usando as anotações do Bean Validation.
- **spring-boot-starter-web**: fornece suporte para desenvolvimento de aplicativos da web usando o Spring MVC.
- **flyway-core**: permite a migração de banco de dados versionado de forma controlada.
- **flyway-mysql**: adiciona suporte específico para o banco de dados MySQL ao Flyway.
- **spring-boot-devtools**: adiciona ferramentas de desenvolvimento para reinicialização automática e configuração rápida.
- **mysql-connector-j**: driver JDBC para integração com o banco de dados MySQL.
- **lombok**: biblioteca que auxilia na redução de código boilerplate, gerando automaticamente getters, setters, construtores e muito mais.
- **spring-boot-starter-test**: fornece suporte para testes de unidade e integração.
- **modelmapper**: biblioteca para mapeamento de objetos.
- **opencsv**: biblioteca para leitura e gravação de arquivos CSV.
- **springdoc-openapi-starter-webmvc-ui**: biblioteca para geração da documentação da API utilizando o OpenAPI.

## Executando a aplicação

Para executar a aplicação, siga as instruções abaixo:

1. Navegue até o diretório raiz do projeto:
   ```
   cd poligonos
   ```

2. Compile o projeto usando o Maven:
   ```
   mvn clean install
   ```

3. Execute o projeto:
   ```
   mvn spring-boot:run
   ```

A aplicação será iniciada e estará disponível em `http://localhost:8080`.

## Documentação da API

A documentação da API pode ser acessada em `http://localhost:8080/swagger-ui.html`.

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir problemas (issues) e enviar pull requests para melhorar este projeto.

## Licença

Este projeto está licenciado sob os termos da licença MIT. Consulte o arquivo [LICENSE](LICENSE) para obter mais informações.

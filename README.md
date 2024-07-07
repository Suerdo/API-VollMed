# VollMed API

Status: Desenvolvimento  
Licença: MIT  

A API VollMed é uma API RESTful para gerenciar consultas médicas, médicos, pacientes e autenticação de usuários. Ela é construída com Spring Boot e utiliza um banco de dados MySQL.

## Índice 📖
- [Recursos](#recursos-✨)
- [Instruções de Execução](#instruções-de-execução-🚀)

## Recursos ✨
- Criar, ler, atualizar e excluir médicos
- Criar, ler, atualizar e excluir pacientes
- Criar, ler e excluir consultas
- Autenticação de usuários com JWT
- Documentação da API com Swagger

## Instruções de Execução 🚀

1. Clone o repositório e navegue até o diretório do projeto:
   ```sh
   git clone https://github.com/seu-usuario/API-VollMed.git
   cd API-VollMed
2. Compile o projeto usando Maven:
   ```sh
      ./mvnw clean install
3. Configure o arquivo application.properties no diretório src/main/resources com as seguintes propriedades:
   ```sh
   spring.datasource.url=jdbc:mysql://localhost/vollmed_api
   spring.datasource.username=root
   spring.datasource.password=root

   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.format_sql=true

   spring.flyway.enabled=true
   spring.flyway.locations=classpath:db/migration

   server.error.include-stacktrace=never

   api.security.token.secret=${JWT_SECRET:12345678}

   springdoc.api-docs.path=/v3/api-docs
   springdoc.swagger-ui.path=/swagger-ui.html

   spring.resources.static-locations=classpath:/META-INF/resources/,classpath:/resources/,classpath:/static/,classpath:/public/
   spring.mvc.static-path-pattern=/swagger-ui/**, /swagger-ui.html
4. Execute o script ``run.cmd`` para iniciar a aplicação e o banco de dados. A aplicação estará disponível em ``http://localhost:8080``
5. Você pode acessar a documentação da API em ``http://localhost:8080/swagger-ui/index.html#/``


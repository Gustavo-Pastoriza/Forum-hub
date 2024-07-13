# Fórum API

## Descrição

Esta é uma API para um fórum, permitindo o gerenciamento de tópicos, usuários e cursos.

## Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Security
- Hibernate
- MySQL
- JWT para autenticação

## Instalação

1. Clone o repositório:
   ```
   git clone https://github.com/seuusuario/forum-api.git
Configure o banco de dados no application.properties:

properties
Copiar código
spring.datasource.url=jdbc:mysql://localhost:3306/forum_hub
spring.datasource.username=root
spring.datasource.password=sua_senha
Execute a aplicação:

```bash
Copiar código
./mvnw spring-boot:run
Endpoints
Autenticação
POST /login: Realiza o login e retorna um token JWT.
Tópicos
GET /topicos: Lista todos os tópicos.
POST /topicos: Cria um novo tópico.
GET /topicos/{id}: Busca um tópico por ID.
PUT /topicos/{id}: Atualiza um tópico existente.
DELETE /topicos/{id}: Exclui um tópico.
Contribuição
Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou pull requests.
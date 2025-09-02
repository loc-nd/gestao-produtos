# API de Gestão de Produtos
Uma API RESTful completa para gerenciamento de produtos, desenvolvida em Spring Boot, com validações, tratamento de erros, documentação com OpenAPI (Swagger), testes unitários e de integração, e suporte a Docker.

## Funcionalidades

- CRUD completo de produtos
- Validações de entrada com Bean Validation
- Tratamento global de exceções com respostas padronizadas
- Documentação interativa com Swagger/OpenAPI
- HATEOAS para links de relacionamento entre recursos
- Testes unitários e de integração
- Integração com banco PostgreSQL via Docker
- Configuração de ambientes (dev, test, prod)

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.3.2
- Spring Data JPA
- Spring HATEOAS
- Spring Validation
- PostgreSQL
- Lombok
- SpringDoc OpenAPI
- JUnit 5 + Mockito
- Docker & Docker Compose

## Pré-requisitos

- Java 17+
- Maven
- Docker e Docker Compose (opcional, para rodar o banco via container)

## Como Executar

1. Clone o repositório

```
git clone https://github.com/seu-usuario/gestao-produtos.git
cd gestao-produtos
````
2. Execute o banco com Docker
```
docker-compose up -d
````
3. Execute a aplicação
```
mvn spring-boot:run
````
A aplicação estará disponível em:
http://localhost:8080

## Documentação da API

Acesse a documentação interativa via Swagger:
http://localhost:8080/swagger-ui.html

```
Método	Rota	Descrição
POST	/produtos	Cria um novo produto
GET	/produtos	Lista todos os produtos
GET	/produtos/{id}	Busca produto por ID
PUT	/produtos/{id}	Atualiza produto por ID
DELETE	/produtos/{id}	Remove produto por ID
````

## Testes

Execute todos os testes:
```
mvn test
````

## Testes unitários

- Controller
- Service
- Repository
- DTO
- Exception Handler

## Testes de integração

- Criação e listagem de produtos
- Validação de regras de negócio

## Docker Compose

```YAML
version: '3.8'

services:
  postgres:
    image: postgres:16
    environment:
      POSTGRES_USER: admin
      POSTGRES_PASSWORD: admin123
      POSTGRES_DB: produtos_db
    ports:
      - "5432:5432"
````

## Exemplo de Requisição

Criar produto

POST /produtos
```
JSON

{
"nome": "A Revolução dos Bichos",
"preco": 29.90,
"descricao": "Verdadeiro clássico moderno...",
"categoria": "Livros",
"isbn": "9788535909555"
}
````


Resposta
```
JSON

{
"idProduto": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
"nome": "A Revolução dos Bichos",
"preco": 29.90,
"descricao": "Verdadeiro clássico moderno...",
"categoria": "Livros",
"isbn": "9788535909555",
"_links": {
"self": {
"href": "http://localhost:8080/produtos/a1b2c3d4-e5f6-7890-abcd-ef1234567890"
}
}
}
````

## Regras de Negócio

- Não é permitido cadastrar dois produtos com o mesmo nome.
- Não é permitido cadastrar dois produtos com o mesmo ISBN.
- O ISBN deve ter exatamente 13 caracteres numéricos.




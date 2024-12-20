[![Continuous Integration with Github](https://github.com/edielson-assis/book-and-cambio-microservices/actions/workflows/docker-publish.yml/badge.svg)](https://github.com/edielson-assis/book-and-cambio-microservices/actions/workflows/docker-publish.yml)
<h1 align="center">BookRate</h1> 

![Badge Concluído](https://img.shields.io/static/v1?label=Status&message=Concluído&color=success&style=for-the-badge)
![Badge Java](https://img.shields.io/static/v1?label=Java&message=17&color=orange&style=for-the-badge&logo=java)
![Badge Springboot](https://img.shields.io/static/v1?label=Springboot&message=v3.4.0&color=brightgreen&style=for-the-badge&logo=spring)
![Badge PostgreSQL](https://img.shields.io/static/v1?label=PostgreSQL&message=v16.4&color=blue&style=for-the-badge&logo=PostgreSQL)
![Badge Docker](https://img.shields.io/static/v1?label=Docker&message=v27.3.1&color=blue&style=for-the-badge&logo=Docker)

<br>


## :book: Descrição do projeto 

<p align="justify">
BookRate é uma aplicação desenvolvida em Java e Spring Boot, utilizando a arquitetura de microserviços. Este projeto conta com uma aplicação dockerizada que pode ser facilmente executada usando Docker Compose.

O sistema permite que os usuários realizem consultas em tempo real das taxas de câmbio. No microserviço de câmbio, foi utilizada a API da AwesomeAPI para obter as taxas de câmbio, que são atualizadas a cada 30 segundos.

Já o microserviço de livros permite que os usuários cadastrem e consultem preços de livros. Por padrão, um livro é cadastrado em dólares, mas o preço pode ser consultado em qualquer moeda.
  
</p><br>

## :bulb: Funcionalidades

### 💰 Taxa de câmbio 

- `Consulta do câmbio`: O usuário pode consultar a taxa de câmbio, pra diversas moedas, em tempo real.

### :toolbox: Cadastro e consulta

- `Cadastro`: Os usuários podem cadastrar livros. 
- `Consulta`: O preço dos livros podem ser exibidos em qualquer moeda.

--------

## Pré-requisito:

- Docker e Docker Compose instalados no sistema. Você pode baixar o Docker Desktop (que já inclui o Docker Compose) a partir do [site oficial do Docker](https://www.docker.com/).


## Como Executar

### Passo 1: Obtenha o arquivo `docker-compose.yml`

Baixe o arquivo `docker-compose.yml` fornecido no repositório. Esse arquivo contém as definições de configuração necessárias para rodar a aplicação e suas dependências, como o banco de dados.

### Passo 2: Execute o Docker Compose

No terminal, navegue até a pasta onde você salvou o `docker-compose.yml` e execute o seguinte comando:

```
docker compose up -d
```

### Passo 3: Verifique os Logs (Opcional)

Para verificar se a aplicação está funcionando corretamente, você pode inspecionar os logs com o comando:

```
docker compose logs -f
```

Esse comando exibirá os logs de todos os containers, permitindo que você veja o status da aplicação e do banco de dados.

### Passo 4: Acesse a Aplicação

Após o Docker Compose iniciar todos os containers, a aplicação estará acessível. Você poderá acessá-la no navegador em:

```
http://localhost:8765/webjars/swagger-ui/index.html
```
Isso fará com que a aplicação seja inicializada na porta 8765.

## Parar e Remover os Containers

```
docker compose down
```
Esse comando encerra a execução dos containers e remove os recursos associados, liberando espaço no sistema.

## Outra alternativa para rodar a aplicação 

Abra o terminal do git bash na pasta onde deseja salvar o projeto e digite o seguinte comando: 

```
git clone git@github.com:edielson-assis/bookrate-microservices.git
```
Depois de clonar o projeto, siga as instruções do passo 2 em diante para configurar e iniciar a aplicação.

## :books: Linguagens, dependencias e libs utilizadas 

- [Java](https://docs.oracle.com/en/java/javase/17/docs/api/index.html)
- [Maven](https://maven.apache.org/ref/3.9.3/maven-core/index.html)
- [Lombok](https://mvnrepository.com/artifact/org.projectlombok/lombok)
- [PostgreSQL](https://www.postgresql.org/docs/16/index.html)
- [Spring Data JPA](https://mvnrepository.com/artifact/org.springframework.data/spring-data-jpa/3.2.1)
- [Bean Validation API](https://mvnrepository.com/artifact/jakarta.validation/jakarta.validation-api/3.0.2)
- [Spring Boot Starter Web](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web)
- [Flyway PostgreSQL](https://mvnrepository.com/artifact/org.flywaydb/flyway-database-postgresql)
- [Flyway Core](https://mvnrepository.com/artifact/org.flywaydb/flyway-core/11.1.0)
- [Swagger](https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-starter-webmvc-ui/2.3.0)
- [Docker](https://docs.docker.com/)
- [Spring cloud](https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-netflix-eureka-server)
- [Actuator](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-actuator)
- [OpenFeign](https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-openfeign)
- [RabbitMQ](https://mvnrepository.com/artifact/org.springframework.amqp/spring-rabbit)
- [Zipkin](https://mvnrepository.com/artifact/io.zipkin.reporter2/zipkin-reporter-brave)

## Licença 

The [Apache License 2.0 License](https://github.com/edielson-assis/bookrate-microservices/blob/main/LICENSE) (Apache License 2.0)

Copyright :copyright: 2024 - BookRate

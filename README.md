LiterAlura

Um aplicativo CLI (Command Line Interface) para buscar e organizar livros utilizando a API pública Gutendex API.

A aplicação permite pesquisar livros de domínio público, salvar informações em um banco PostgreSQL e consultar autores e livros utilizando filtros simples diretamente no terminal.

Sobre o projeto

O LiterAlura é uma aplicação de linha de comando desenvolvida em Java utilizando Spring Boot.

O sistema consome a API Gutendex para buscar livros e permite armazenar os resultados localmente em um banco de dados PostgreSQL. A partir disso, o usuário pode consultar livros, listar autores e aplicar filtros como idioma ou período de vida dos autores.

Este projeto foi desenvolvido com foco em prática de:

consumo de APIs externas

persistência de dados com JPA

modelagem de entidades

organização em camadas (service, repository, client)

Tecnologias utilizadas

Java 17+

Spring Boot 3.3

Spring Data JPA

Hibernate ORM

PostgreSQL

Maven

Funcionalidades

O menu principal oferece as seguintes opções:

1 - Buscar livro pelo título
Realiza uma busca na API Gutendex e permite salvar o livro no banco de dados.

2 - Listar livros registrados
Exibe todos os livros salvos localmente.

3 - Listar autores
Mostra os autores cadastrados junto com informações básicas.

4 - Listar autores vivos em determinado ano
Filtra autores que estavam vivos em um ano específico.

5 - Listar livros por idioma
Exibe os livros salvos filtrando pelo idioma (ex: pt, en, es).

Como rodar o projeto localmente
Pré-requisitos

Java 17+

Maven 3.6+

PostgreSQL 12+

1. Clonar o repositório
git clone https://github.com/BrunoDevFreitas/literalura.git
cd literalura
2. Configurar variáveis de ambiente

Copie o arquivo de exemplo:

cp .env.example .env

Edite o .env com suas credenciais do PostgreSQL:

DB_URL=jdbc:postgresql://localhost:5432/literatura
DB_USER=postgres
DB_PASSWORD=sua_senha
3. Criar o banco de dados
psql -U postgres -c "CREATE DATABASE literatura;"
4. Compilar e executar

Com Maven:

mvn clean install
mvn spring-boot:run

Ou executando o JAR:

java -jar target/literalura-0.0.1-SNAPSHOT.jar
Variáveis de ambiente
Variável	Descrição	Exemplo
DB_URL	URL de conexão com PostgreSQL	jdbc:postgresql://localhost:5432/literatura
DB_USER	Usuário do banco	postgres
DB_PASSWORD	Senha do banco	sua_senha
Exemplo de uso
=== LiterAlura ===

1 - Buscar livro pelo título
2 - Listar livros registrados
3 - Listar autores
4 - Listar autores vivos em determinado ano
5 - Listar livros por idioma
0 - Sair

Escolha uma opção: 1

Digite o título do livro: Dom Casmurro

Buscando na API Gutendex...

Encontrados 3 resultados

1. Dom Casmurro
   Autor: Machado de Assis
   Downloads: 15000

Escolha um livro para salvar: 1

Livro salvo com sucesso.
Estrutura do projeto
literalura/
 ├── src
 │   ├── main
 │   │   ├── java/com/literalura/literalura
 │   │   │   ├── client
 │   │   │   ├── dto
 │   │   │   ├── model
 │   │   │   ├── principal
 │   │   │   ├── repository
 │   │   │   └── service
 │   │   └── resources
 │   │       └── application.properties
 │   └── test
 ├── pom.xml
 ├── .env.example
 ├── .gitignore
 └── README.md
Entidades principais
Book

Representa um livro salvo no banco de dados.

id

título

idioma

número de downloads

referência ao autor

Author

Representa um autor.

id

nome

ano de nascimento

ano de falecimento

lista de livros

Contribuição

Sugestões e melhorias são bem-vindas.
Caso encontre algum problema ou tenha alguma ideia de melhoria, fique à vontade para abrir uma issue ou enviar um pull request.

Observação de segurança

As credenciais do banco de dados não devem ser commitadas no repositório.

Utilize variáveis de ambiente ou o arquivo .env local para armazenar essas informações.

Autor

Projeto desenvolvido para prática de backend com Java, Spring Boot e integração com APIs externas.

 Resumo:
Seu README já estava bom, eu apenas:

corrigi pequenos detalhes

deixei a leitura mais natural

removi alguns trechos que pareciam IA

melhorei organização para recrutadores

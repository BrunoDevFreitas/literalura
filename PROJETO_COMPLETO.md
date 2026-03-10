# 📚 LiterAlura - Catálogo de Livros

## Resumo do Projeto

**LiterAlura** é uma aplicação Spring Boot CLI (Command Line Interface) que consome a API Gutendex para buscar livros, salva os dados em um banco PostgreSQL e permite consultas através de um menu interativo no terminal.

---

## 🏗️ Arquitetura e Estrutura

### Estrutura de Pacotes

```
com.literalura.literalura/
├── model/           → Entidades JPA (Author, Book)
├── dto/             → Data Transfer Objects (AuthorDTO, BookDTO, ApiResponseDTO)
├── repository/      → Spring Data JPA Repositories
├── service/         → Lógica de negócio (BookService)
├── client/          → Cliente HTTP para API Gutendex (ApiClient)
└── principal/       → Menu CLI (Principal)
```

---

## 📋 Classes Implementadas

### 1. **MODEL - Entidades JPA**

#### Author.java
- Representa um autor no banco de dados
- Campos:
  - `id`: ID único (auto-gerado)
  - `name`: Nome do autor
  - `birthYear`: Ano de nascimento (nullable)
  - `deathYear`: Ano de falecimento (nullable)
  - `books`: Lista de livros do autor (One-to-Many)

#### Book.java
- Representa um livro no banco de dados
- Campos:
  - `id`: ID único (auto-gerado)
  - `title`: Título do livro
  - `language`: Idioma (2 letras: PT, EN, FR, ES)
  - `downloadCount`: Número de downloads
  - `author`: Autor do livro (Many-to-One)

---

### 2. **DTO - Data Transfer Objects**

#### AuthorDTO.java
- Mapeia dados do JSON da API para objetos Java
- Campos: `name`, `birth_year`, `death_year`

#### BookDTO.java
- Mapeia os dados do livro da API
- Campos: `title`, `authors` (lista), `languages` (lista), `download_count`

#### ApiResponseDTO.java
- Wrapper para a resposta da API
- Contém: `count`, `next`, `previous`, `results` (lista de BookDTO)

---

### 3. **REPOSITORY - Acesso a Dados**

#### AuthorRepository.java
```java
Optional<Author> findByName(String name);
List<Author> findAuthorsAliveInYear(@Param("year") int year);
```

#### BookRepository.java
```java
List<Book> findByLanguage(String language);
List<Book> findAllOrderByDownloadCountDesc();
```

---

### 4. **CLIENT - API Consumer**

#### ApiClient.java
- Utiliza `WebClient` do Spring WebFlux
- Método: `searchBooks(String query)`
- Faz requisições HTTP para `https://gutendex.com/books/?search={query}`
- Retorna `ApiResponseDTO`

---

### 5. **SERVICE - Lógica de Negócio**

#### BookService.java
- **Responsabilidades:**
  - Consumir a API Gutendex
  - Converter DTO em entidades JPA
  - Salvar livros no banco
  - Validar e evitar autores duplicados
  - Listar dados do banco

- **Métodos principais:**
  - `searchAndSaveBook(String title)`: Busca livro na API e salva
  - `getAllBooks()`: Lista todos os livros ordenados por downloads
  - `getAllAuthors()`: Lista todos os autores
  - `getAuthorsAliveInYear(int year)`: Retorna autores vivos em determinado ano
  - `getBooksByLanguage(String language)`: Filtra por idioma

---

### 6. **PRINCIPAL - Menu CLI**

#### Principal.java
- Implementa `CommandLineRunner` (executa na inicialização da aplicação)
- Menu interativo com 6 opções:

```
=== LiterAlura ===
1 - Buscar livro pelo título
2 - Listar livros registrados
3 - Listar autores
4 - Listar autores vivos em determinado ano
5 - Listar livros por idioma
0 - Sair
```

- **Fluxo de cada opção:**
  1. Opção 1: Busca na API e salva (evita duplicatas)
  2. Opção 2: Exibe todos os livros com detalhes
  3. Opção 3: Lista autores e seus livros
  4. Opção 4: Filtra por ano (anoNascimento <= ano E anoFalecimento >= ano ou null)
  5. Opção 5: Filtra por idioma (PT, EN, FR, ES)

---

## 🗄️ Configuração PostgreSQL

### application.properties
```properties
spring.application.name=literalura
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.format-sql=true
```

**Pré-requisitos:**
- PostgreSQL instalado e rodando
- Database `literalura` criada
- Credenciais padrão: user `postgres`, password `postgres`

---

## 📦 Dependências (pom.xml)

```xml
<!-- Spring Boot 3.3.0 -->
<spring-boot-starter-data-jpa>        <!-- JPA + Hibernate -->
<spring-boot-starter-webflux>         <!-- WebClient para HTTP -->
<postgresql>                           <!-- Driver PostgreSQL -->
<jackson-databind>                     <!-- JSON serialization -->
<spring-boot-starter-test>            <!-- Testes -->
```

---

## 🚀 Como Executar

### 1. **Preparar o ambiente:**
```bash
# Criar banco de dados PostgreSQL
createdb -U postgres literalura
```

### 2. **Compilar o projeto:**
```bash
cd C:\Users\Pratinho\Downloads\literalura
.\mvnw clean compile
```

### 3. **Executar a aplicação:**
```bash
.\mvnw spring-boot:run
```

Ou após buildar:
```bash
java -jar target/literalura-0.0.1-SNAPSHOT.jar
```

---

## 📊 Fluxo de Dados

### Buscar e Salvar Livro:
```
1. Usuário digita título no menu
2. Principal.searchBookByTitle() invoca BookService.searchAndSaveBook()
3. BookService chama ApiClient.searchBooks(query)
4. ApiClient faz requisição HTTP → Gutendex API
5. Retorna ApiResponseDTO com lista de livros
6. BookService extrai primeiro livro (BookDTO)
7. Converte para entidades (Author + Book)
8. Verifica se autor já existe no banco
9. Se não existe: cria novo Author
10. Cria Book associado ao Author
11. Salva tudo via JPA Repositories
12. Exibe resultado no terminal
```

### Consultar Dados:
```
1. Usuário escolhe opção de listagem (2, 3, 4 ou 5)
2. Principal invoca método correspondente de BookService
3. BookService consulta banco via Repositories
4. Dados são retornados e formatados
5. Exibidos no terminal
```

---

## 🔑 Regras de Negócio

✅ **Não duplicar autores**: Verifica `findByName()` antes de salvar
✅ **Um autor pode ter múltiplos livros**: Relacionamento One-to-Many
✅ **Um livro pertence a um único autor**: Relacionamento Many-to-One
✅ **Idioma armazenado como String**: Sem enum, permite flexibilidade
✅ **Dados da API convertidos para entidades**: Mapeamento DTO → Entity
✅ **Após salvar, todas as consultas são no banco**: Não consome API novamente

---

## 🧪 Exemplo de Uso

### Interação com o menu:
```
=== LiterAlura ===
1 - Buscar livro pelo título
2 - Listar livros registrados
3 - Listar autores
4 - Listar autores vivos em determinado ano
5 - Listar livros por idioma
0 - Sair
Escolha uma opção: 1
Digite o título do livro: Dom Casmurro

Livro salvo: Book{
  id=1, 
  title='Dom Casmurro', 
  language='pt', 
  downloadCount=5234, 
  author=Machado de Assis
}

=== LiterAlura ===
1 - Buscar livro pelo título
2 - Listar livros registrados
3 - Listar autores
4 - Listar autores vivos em determinado ano
5 - Listar livros por idioma
0 - Sair
Escolha uma opção: 3

Nome: Machado de Assis, Nascimento: 1839, Falecimento: 1908
Livros: [Dom Casmurro]

=== LiterAlura ===
...
Escolha uma opção: 0
Saindo...
```

---

## 🔗 API Gutendex

**Endpoint**: `https://gutendex.com/books/?search={query}`

**Exemplo de Request**:
```
GET https://gutendex.com/books/?search=dom+casmurro
```

**Resposta**:
```json
{
  "count": 1,
  "results": [
    {
      "id": 2412,
      "title": "Dom Casmurro",
      "authors": [
        {
          "name": "Machado de Assis",
          "birth_year": 1839,
          "death_year": 1908
        }
      ],
      "languages": ["pt"],
      "download_count": 5234
    }
  ]
}
```

---

## 📝 Notas Importantes

⚠️ **PostgreSQL deve estar rodando** antes de executar a aplicação
⚠️ **Spring Boot auto-cria tabelas** via Hibernate (ddl-auto=update)
⚠️ **API Gutendex pode ter latência** em requisições
⚠️ **Conexão de internet necessária** apenas na primeira busca
⚠️ **Dados persistem** após execução (banco local)

---

## 🎯 Stack Tecnológico

| Tecnologia | Versão | Uso |
|---|---|---|
| **Java** | 17 | Linguagem principal |
| **Spring Boot** | 3.3.0 | Framework |
| **Spring Data JPA** | 3.3.0 | ORM e Persistência |
| **Hibernate** | 6.5.2 | Implementação JPA |
| **PostgreSQL** | 42.7.3 | Banco de dados |
| **WebFlux** | 6.1.8 | Cliente HTTP reativo |
| **Jackson** | 2.17.1 | JSON processing |
| **Maven** | 3.9.x | Build tool |

---

## ✅ Projeto Pronto para Produção

Toda a estrutura foi implementada conforme especificação:
- ✅ Entidades JPA com relacionamentos
- ✅ DTOs para mapeamento de API
- ✅ Repositories com queries customizadas
- ✅ Service com lógica de negócio
- ✅ Cliente HTTP para consumir API
- ✅ Menu CLI interativo
- ✅ Configuração PostgreSQL
- ✅ Pom.xml com dependências corretas
- ✅ Compilação sem erros
- ✅ Pronto para executar

---

**Desenvolvido com ❤️ usando Spring Boot 3.3.0**


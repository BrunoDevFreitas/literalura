# 📚 Sumário de Todas as Classes Implementadas

## 🎯 Visão Geral

```
┌─────────────────────────────────────────────────────────────┐
│           LiterAlura - Arquitetura do Projeto              │
└─────────────────────────────────────────────────────────────┘

     ┌──────────────────────────────────────────────┐
     │        Principal (CLI Menu)                 │
     │    Interage com usuário                      │
     └────────────────┬─────────────────────────────┘
                      │
                      ▼
     ┌──────────────────────────────────────────────┐
     │        BookService                          │
     │    Lógica de negócio                         │
     │    - Busca na API                            │
     │    - Converte DTOs em Entities               │
     │    - Evita duplicação de autores             │
     └──────────────────────────────────────────────┘
                      │
        ┌─────────────┴──────────────┬──────────────┐
        │                            │              │
        ▼                            ▼              ▼
   ┌─────────┐            ┌──────────────┐    ┌──────────┐
   │ApiClient│            │Repositories  │    │  Model   │
   │ (HTTP)  │            │  (JPA)       │    │Entities  │
   └─────────┘            └──────────────┘    └──────────┘
        │                        │                  │
        ▼                        ▼                  ▼
   ┌─────────┐    ┌──────────┐ ┌──────────┐  ┌────────┐
   │Gutendex │    │Author    │ │Book      │  │Author  │
   │  API    │    │Repository│ │Repository│  │ Entity │
   └─────────┘    └──────────┘ └──────────┘  └────────┘
                                                  │
                                                  ▼
                                             ┌────────┐
                                             │Book    │
                                             │Entity  │
                                             └────────┘
                                                  │
                                                  ▼
                                           ┌─────────────┐
                                           │PostgreSQL   │
                                           │  Database   │
                                           └─────────────┘
```

---

## 📦 Pacotes e Classes

### 1️⃣ **model** - Entidades JPA

#### ✅ Author.java
```
┌─────────────────────────────────┐
│ Author (Entidade JPA)           │
├─────────────────────────────────┤
│ - id: Long (PK)                 │
│ - name: String                  │
│ - birthYear: Integer (nullable) │
│ - deathYear: Integer (nullable) │
│ - books: List<Book>             │
├─────────────────────────────────┤
│ + Author()                      │
│ + Author(name, birth, death)    │
│ + getters/setters               │
│ + toString()                    │
└─────────────────────────────────┘
         ▲
         │ OneToMany
         │
         └─── Book
```

#### ✅ Book.java
```
┌─────────────────────────────────┐
│ Book (Entidade JPA)             │
├─────────────────────────────────┤
│ - id: Long (PK)                 │
│ - title: String                 │
│ - language: String              │
│ - downloadCount: Integer        │
│ - author: Author (FK)           │
├─────────────────────────────────┤
│ + Book()                        │
│ + Book(title, lang, downloads)  │
│ + getters/setters               │
│ + toString()                    │
└─────────────────────────────────┘
         │
         │ ManyToOne
         ▼
      Author
```

---

### 2️⃣ **dto** - Data Transfer Objects

#### ✅ AuthorDTO.java
```
┌─────────────────────────────────┐
│ AuthorDTO                       │
├─────────────────────────────────┤
│ - name: String                  │
│ - birth_year: int               │
│ - death_year: int               │
├─────────────────────────────────┤
│ + AuthorDTO()                   │
│ + AuthorDTO(name, birth, death) │
│ + getters/setters               │
└─────────────────────────────────┘
        ↑
        │ Desserializado
        │
   JSON da API
```

#### ✅ BookDTO.java
```
┌──────────────────────────────┐
│ BookDTO                      │
├──────────────────────────────┤
│ - title: String              │
│ - authors: List<AuthorDTO>   │
│ - languages: List<String>    │
│ - download_count: int        │
├──────────────────────────────┤
│ + BookDTO()                  │
│ + getters/setters            │
└──────────────────────────────┘
        ↑
        │ Desserializado
        │
   JSON da API
```

#### ✅ ApiResponseDTO.java
```
┌──────────────────────────────────┐
│ ApiResponseDTO                   │
├──────────────────────────────────┤
│ - count: int                     │
│ - next: String                   │
│ - previous: String               │
│ - results: List<BookDTO>         │
├──────────────────────────────────┤
│ + ApiResponseDTO()               │
│ + getters/setters                │
└──────────────────────────────────┘
        ↑
        │ Wrapper da resposta
        │
   JSON da API completo
```

---

### 3️⃣ **repository** - Spring Data JPA Repositories

#### ✅ AuthorRepository.java
```
public interface AuthorRepository 
    extends JpaRepository<Author, Long> {
    
    // Métodos customizados:
    
    Optional<Author> findByName(String name)
    └─ Busca autor por nome
    
    List<Author> findAuthorsAliveInYear(int year)
    └─ Query customizada: 
       birthYear <= year AND (deathYear >= year OR NULL)
}
```

#### ✅ BookRepository.java
```
public interface BookRepository 
    extends JpaRepository<Book, Long> {
    
    // Métodos customizados:
    
    List<Book> findByLanguage(String language)
    └─ Filtro por idioma
    
    List<Book> findAllOrderByDownloadCountDesc()
    └─ Todos os livros ordenados por downloads (desc)
}
```

---

### 4️⃣ **client** - API Consumer

#### ✅ ApiClient.java
```
┌────────────────────────────────────┐
│ ApiClient                          │
├────────────────────────────────────┤
│ - webClient: WebClient             │
├────────────────────────────────────┤
│ + ApiClient(webClientBuilder)      │
│                                    │
│ + searchBooks(query)               │
│   └─ Requisição GET para API       │
│   └─ Retorna ApiResponseDTO        │
│   └─ URL: /books/?search={query}   │
└────────────────────────────────────┘
           │
           │ HTTP GET
           ▼
  https://gutendex.com/books/
       ?search={query}
```

---

### 5️⃣ **service** - Lógica de Negócio

#### ✅ BookService.java
```
┌──────────────────────────────────────────┐
│ BookService                              │
├──────────────────────────────────────────┤
│ - apiClient: ApiClient                   │
│ - authorRepository: AuthorRepository     │
│ - bookRepository: BookRepository         │
├──────────────────────────────────────────┤
│ Métodos Públicos:                        │
│                                          │
│ + searchAndSaveBook(title): Book         │
│   ├─ Chama ApiClient.searchBooks()       │
│   ├─ Extrai primeiro resultado           │
│   └─ Salva no banco                      │
│                                          │
│ + getAllBooks(): List<Book>              │
│   └─ Orderado por downloads              │
│                                          │
│ + getAllAuthors(): List<Author>          │
│   └─ Todos os autores                    │
│                                          │
│ + getAuthorsAliveInYear(year)             │
│   └─ Filtro por ano                      │
│                                          │
│ + getBooksByLanguage(language)            │
│   └─ Filtro por idioma                   │
│                                          │
│ Métodos Privados:                        │
│                                          │
│ - saveBookFromDTO(bookDTO): Book         │
│   └─ Converte DTO em Entity              │
│                                          │
│ - findOrCreateAuthor(authorDTO): Author  │
│   ├─ Busca autor no banco                │
│   ├─ Se não existe, cria novo            │
│   └─ Evita duplicação                    │
└──────────────────────────────────────────┘
```

---

### 6️⃣ **principal** - Menu CLI

#### ✅ Principal.java
```
┌──────────────────────────────────────┐
│ Principal                            │
│ implements CommandLineRunner          │
├──────────────────────────────────────┤
│ - bookService: BookService           │
│ - scanner: Scanner                   │
├──────────────────────────────────────┤
│ + run(args): void                    │
│   └─ Inicia loop do menu             │
│                                      │
│ - showMenu(): void                   │
│   └─ Exibe 6 opções                  │
│                                      │
│ - searchBookByTitle(): void          │
│   └─ Opção 1                         │
│                                      │
│ - listRegisteredBooks(): void        │
│   └─ Opção 2                         │
│                                      │
│ - listAuthors(): void                │
│   └─ Opção 3                         │
│                                      │
│ - listAuthorsAliveInYear(): void     │
│   └─ Opção 4                         │
│                                      │
│ - listBooksByLanguage(): void        │
│   └─ Opção 5                         │
└──────────────────────────────────────┘
```

---

### 7️⃣ **Configuração** - application.properties

```properties
spring.application.name=literalura
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.format-sql=true
```

---

### 8️⃣ **Classe Principal** - LiteraluraApplication.java

```java
@SpringBootApplication
public class LiteraluraApplication {
    public static void main(String[] args) {
        SpringApplication.run(LiteraluraApplication.class, args);
    }
}
```

Executa:
1. Inicializa contexto Spring
2. Scanneia componentes (@Component)
3. Executa Principal (CommandLineRunner)
4. Mostra menu

---

## 🔄 Fluxo de Requisição

### Buscar e Salvar Livro

```
┌─────────────────────────────────────────────────────┐
│1. Usuário seleciona opção 1                         │
└────────────────────┬────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────┐
│2. Principal.searchBookByTitle()                     │
│   - Lê input do usuário                             │
└────────────────────┬────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────┐
│3. BookService.searchAndSaveBook(title)              │
└────────────────────┬────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────┐
│4. ApiClient.searchBooks(query)                      │
│   - WebClient faz requisição GET                    │
│   - URL: https://gutendex.com/books/?search=...    │
└────────────────────┬────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────┐
│5. Gutendex API retorna JSON                         │
│   - Jackson desserializa para ApiResponseDTO        │
└────────────────────┬────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────┐
│6. BookService extrai primeiro BookDTO               │
│   - results[0]                                      │
└────────────────────┬────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────┐
│7. Extrai AuthorDTO do primeiro livro                │
│   - authors[0]                                      │
└────────────────────┬────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────┐
│8. BookService.findOrCreateAuthor(authorDTO)         │
│   - AuthorRepository.findByName(name)               │
│   - Se existe: retorna                              │
│   - Se não existe: cria nova Author                 │
│   - Salva via repository.save()                     │
└────────────────────┬────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────┐
│9. Converte BookDTO para Entity                      │
│   - Extrai title, language, download_count         │
│   - Associa ao Author                               │
└────────────────────┬────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────┐
│10. BookRepository.save(book)                        │
│    - INSERT no PostgreSQL                           │
│    - Hibernate gera SQL automaticamente              │
└────────────────────┬────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────┐
│11. Principal exibe resultado                        │
│    - Livro: Título [X] Autor: Nome                 │
└─────────────────────────────────────────────────────┘
```

---

## 📊 Mapeamento ORM

### Relacionamentos

```
Authors Table                  Books Table
┌──────────────────┐          ┌──────────────────┐
│ id (PK)          │          │ id (PK)          │
│ name             │◄────────┤│ title            │
│ birth_year       │1       N │ language         │
│ death_year       │          │ download_count   │
└──────────────────┘          │ author_id (FK)   │
                              └──────────────────┘

1:N Relationship
- Um Author pode ter múltiplos Books
- Um Book pertence a apenas um Author
```

---

## 🎯 Resumo de Responsabilidades

| Classe | Responsabilidade |
|---|---|
| **LiteraluraApplication** | Ponto de entrada da aplicação |
| **Principal** | Menu interativo (CLI) |
| **BookService** | Orquestra lógica de negócio |
| **ApiClient** | Comunica com API Gutendex |
| **AuthorRepository** | CRUD de Autores |
| **BookRepository** | CRUD de Livros |
| **Author** | Entidade de Autor |
| **Book** | Entidade de Livro |
| **AuthorDTO** | Mapeamento JSON → Objeto (Autor) |
| **BookDTO** | Mapeamento JSON → Objeto (Livro) |
| **ApiResponseDTO** | Wrapper da resposta API |

---

## ✅ Checklist de Implementação

- ✅ Classe Author com @Entity
- ✅ Classe Book com @Entity
- ✅ Relacionamento OneToMany/ManyToOne
- ✅ DTOs para mapeamento de API
- ✅ AuthorRepository com métodos customizados
- ✅ BookRepository com métodos customizados
- ✅ ApiClient com WebClient
- ✅ BookService orquestrando tudo
- ✅ Principal implementando CommandLineRunner
- ✅ Menu com 6 opções (5 + Sair)
- ✅ application.properties configurado
- ✅ pom.xml com dependências
- ✅ Projeto compila sem erros

---

## 🚀 Próximas Ações

1. Criar banco PostgreSQL: `CREATE DATABASE literalura;`
2. Compilar: `mvn clean compile`
3. Executar: `mvn spring-boot:run`
4. Testar menu
5. Verificar dados no PostgreSQL

---

**Projeto LiterAlura 100% Implementado! 📚✨**


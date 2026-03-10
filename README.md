# LiterAlura

Um aplicativo CLI para descobrir e organizar livros da [API Gutendex](https://gutendex.com/). Busque livros de escritores famosos, organize por idioma e veja quais autores estavam vivos em determinadas épocas.

## O que é?

LiterAlura é uma aplicação de linha de comando que conecta à API Gutendex para buscar livros de domínio público. Você pode salvar seus livros favoritos em um banco de dados PostgreSQL, filtrar por idioma e explorar informações sobre os autores.

## Tecnologias

- **Java 17+**
- **Spring Boot 3.3.0**
- **Spring Data JPA**
- **Hibernate ORM**
- **PostgreSQL**
- **Maven**

## Funcionalidades

O menu principal oferece 5 opções:

1. **Buscar livro pelo título** - Faz buscas na API Gutendex e permite salvar localmente
2. **Listar livros registrados** - Mostra todos os livros que você salvou
3. **Listar autores** - Lista todos os autores com seus respectivos livros
4. **Listar autores vivos em determinado ano** - Filtra autores que estavam vivos em um ano específico
5. **Listar livros por idioma** - Filtra livros salvos pelo idioma (ex: português, inglês, espanhol)

## Como rodar localmente

### Pré-requisitos

- Java 17+
- Maven 3.6+
- PostgreSQL 12+

### Instalação

1. Clone o repositório:
```bash
git clone https://github.com/seu-usuario/literalura.git
cd literalura
```

2. Configure as variáveis de ambiente. Copie o arquivo `.env.example` para `.env`:
```bash
cp .env.example .env
```

3. Edite o arquivo `.env` com suas credenciais do PostgreSQL:
```ini
DB_URL=jdbc:postgresql://localhost:5432/literatura
DB_USER=postgres
DB_PASSWORD=sua_senha_aqui
```

4. Crie o banco de dados PostgreSQL:
```bash
psql -U postgres -c "CREATE DATABASE literatura;"
```

5. Compile e execute o projeto:
```bash
mvn clean install
mvn spring-boot:run
```

Ou execute o JAR diretamente:
```bash
java -jar target/literalura-0.0.1-SNAPSHOT.jar
```

## Variáveis de Ambiente

Estas variáveis precisam estar definidas para a aplicação funcionar:

| Variável | Descrição | Exemplo |
|----------|-----------|---------|
| `DB_URL` | URL de conexão do PostgreSQL | `jdbc:postgresql://localhost:5432/literatura` |
| `DB_USER` | Usuário do PostgreSQL | `postgres` |
| `DB_PASSWORD` | Senha do PostgreSQL | `sua_senha_secreta` |

### No Windows (PowerShell):
```powershell
$env:DB_URL = "jdbc:postgresql://localhost:5432/literatura"
$env:DB_USER = "postgres"
$env:DB_PASSWORD = "sua_senha"
```

### No macOS/Linux (Bash):
```bash
export DB_URL=jdbc:postgresql://localhost:5432/literatura
export DB_USER=postgres
export DB_PASSWORD=sua_senha
```

Ou adicione ao seu arquivo `.env`:
```ini
DB_URL=jdbc:postgresql://localhost:5432/literatura
DB_USER=postgres
DB_PASSWORD=sua_senha
```

## Exemplo de Uso

```
════════════════════════════════════════════════════════════════════════════════
                            === LiterAlura ===
════════════════════════════════════════════════════════════════════════════════

1 - Buscar livro pelo título (API Gutendex)
2 - Listar livros registrados
3 - Listar autores
4 - Listar autores vivos em determinado ano
5 - Listar livros por idioma
0 - Sair

Escolha uma opção: 1

Digite o título do livro: Dom Casmurro

🔍 Buscando na API Gutendex...

✓ Encontrados 3 resultado(s):

1. Título: "Dom Casmurro"
   Autor: Machado de Assis
   Downloads: 15000

2. Título: "Dom Casmurro - Annotated Edition"
   Autor: Machado de Assis
   Downloads: 2500

3. Título: "Don Quixote"
   Autor: Miguel de Cervantes
   Downloads: 50000

Escolha um livro para salvar (1-3) ou 0 para cancelar: 1

⏳ Salvando livro na base de dados...

✅ Livro salvo com sucesso!
━━━━━━━━━━━━━━━━━━━━━━━━━━
   Título: Dom Casmurro
   Autor: Machado de Assis
   Idioma: pt
   Downloads: 15000
━━━━━━━━━━━━━━━━━━━━━━━━━━
```

## Estrutura do Projeto

```
literalura/
├── src/
│   ├── main/
│   │   ├── java/com/literalura/literalura/
│   │   │   ├── LiteraluraApplication.java
│   │   │   ├── client/ApiClient.java           # Cliente da API Gutendex
│   │   │   ├── dto/                            # Data Transfer Objects
│   │   │   ├── model/                          # Entidades JPA
│   │   │   ├── principal/Principal.java        # Menu da aplicação
│   │   │   ├── repository/                     # Operações de banco
│   │   │   └── service/BookService.java        # Lógica de negócio
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── pom.xml
├── .env.example
├── .gitignore
└── README.md
```

## Entidades Principais

### Book
Representa um livro salvo no banco:
- ID (gerado automaticamente)
- Título (até 1000 caracteres)
- Idioma (ex: "pt", "en", "es")
- Número de downloads
- Referência ao Autor

### Author
Representa um autor:
- ID (gerado automaticamente)
- Nome
- Ano de nascimento
- Ano de morte (null se ainda vivo)
- Lista de Livros

## Contribuindo

Se encontrou um bug ou tem uma sugestão, sinta-se livre para abrir uma issue ou fazer um pull request.

## Licença

Este projeto é de código aberto. Fique livre para usar, modificar e compartilhar.

## Autor

Desenvolvido como projeto de aprendizado em Java, Spring Boot e PostgreSQL.

---

**Nota:** Sempre mantenha suas credenciais de banco de dados em segurança. Nunca comite o arquivo `.env` no repositório.


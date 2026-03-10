# 📚 LiterAlura - Melhorias Implementadas

## 🎯 Objetivo
Melhorar a lógica de consumo da API Gutendex para permitir que o usuário escolha entre até 5 resultados ao invés de salvar automaticamente o primeiro livro encontrado.

---

## ✅ Mudanças Realizadas

### 1. **BookService.java** - Refatoração Completa
**Arquivo:** `src/main/java/com/literalura/literalura/service/BookService.java`

#### Métodos Alterados:
- ❌ **Removido:** `searchAndSaveBook(String title)` - Salvava automaticamente o primeiro resultado
- ✅ **Novo:** `searchBooks(String title)` - Retorna até 5 resultados da API sem salvar
- ✅ **Novo:** `saveBook(BookDTO bookDTO)` - Salva um livro específico com validação de duplicatas
- ✅ **Novo:** `isBookAlreadyExists(BookDTO bookDTO)` - Valida se o livro já existe no banco
- ✅ **Refatorado:** `saveBookFromDTO()` - Agora é privado e chamado apenas por `saveBook()`

#### Fluxo da API:
```
searchBooks(title)
    ↓
ApiClient.searchBooks(title)
    ↓
Retorna até 5 resultados
    ↓
Exibe resultados para o usuário
    ↓
Usuário escolhe
    ↓
saveBook(BookDTO)
    ↓
isBookAlreadyExists? → true: Retorna null | false: Salva no banco
    ↓
saveBookFromDTO()
```

---

### 2. **BookRepository.java** - Nova Funcionalidade
**Arquivo:** `src/main/java/com/literalura/literalura/repository/BookRepository.java`

#### Novo Método:
```java
@Query("SELECT b FROM Book b WHERE b.title = :title AND b.author.name = :authorName")
Optional<Book> findByTitleAndAuthorName(String title, String authorName);
```

**Propósito:** Verificar se um livro já existe no banco de dados (mesmo título + mesmo autor)

---

### 3. **Principal.java** - Nova Interface Interativa
**Arquivo:** `src/main/java/com/literalura/literalura/principal/Principal.java`

#### Mudanças no Método `searchBookByTitle()`:

**Antes:**
```
Digite o título do livro: dom quixote
Livro salvo: Book{...}
```

**Agora:**
```
Digite o título do livro: dom quixote

Buscando livros na API Gutendex...

=== Resultados encontrados ===

1. "Wit and Wisdom of Don Quixote" por Cervantes Saavedra, Miguel de
   Downloads: 593

2. "Don Quixote" por Cervantes Saavedra, Miguel de
   Downloads: 2150

3. "The History of Don Quixote, Volume 1" por Cervantes, Miguel de
   Downloads: 1200

... (até 5 resultados)

Escolha um livro para salvar (1-5) ou 0 para cancelar: 2

✓ Livro salvo com sucesso!
  Título: Don Quixote
  Autor: Cervantes Saavedra, Miguel de
  Idioma: en
  Downloads: 2150
```

#### Melhorias Adicionais:
- ✅ Validação de entrada (não aceita título vazio)
- ✅ Mensagens de erro mais claras
- ✅ Formatação melhorada de saída
- ✅ Validação de duplicatas com feedback ao usuário
- ✅ Tratamento de exceções melhorado
- ✅ Interface mais amigável em todos os métodos

---

## 🔄 Fluxo Completo da Operação

```
┌─────────────────────────────────────────────┐
│ Usuário escolhe opção 1 (Buscar livro)     │
└──────────────┬──────────────────────────────┘
               ↓
┌─────────────────────────────────────────────┐
│ Digita título do livro                      │
│ Exemplo: "dom quixote"                      │
└──────────────┬──────────────────────────────┘
               ↓
┌─────────────────────────────────────────────┐
│ BookService.searchBooks(title)              │
│ ↓                                           │
│ ApiClient faz requisição GET                │
│ Endpoint: /books/?search=dom+quixote        │
│ ↓                                           │
│ Retorna até 5 resultados                    │
└──────────────┬──────────────────────────────┘
               ↓
┌─────────────────────────────────────────────┐
│ Principal exibe 5 opções:                   │
│ 1. "Wit and Wisdom of Don Quixote"         │
│ 2. "Don Quixote"                           │
│ 3. "The History of Don Quixote, Vol. 1"    │
│ ... (até 5)                                 │
└──────────────┬──────────────────────────────┘
               ↓
┌─────────────────────────────────────────────┐
│ Usuário escolhe opção (1-5 ou 0 para sair) │
└──────────────┬──────────────────────────────┘
               ↓
┌─────────────────────────────────────────────┐
│ BookService.saveBook(selectedBook)          │
│ ↓                                           │
│ isBookAlreadyExists()?                      │
│   → true: Retorna null (livro duplicado)    │
│   → false: Continua                         │
│ ↓                                           │
│ BookService.findOrCreateAuthor()            │
│ ↓                                           │
│ Salva autor no banco (se novo)              │
│ ↓                                           │
│ Salva livro no banco                        │
│ ↓                                           │
│ Retorna Book salvo                          │
└──────────────┬──────────────────────────────┘
               ↓
┌─────────────────────────────────────────────┐
│ Exibe mensagem de sucesso/erro              │
│ Volta ao menu principal                     │
└─────────────────────────────────────────────┘
```

---

## 🛡️ Validações Implementadas

1. **Duplicata de Livros:**
   - Verifica título + nome do autor
   - Impede salvamento se livro já existe

2. **Validação de Entrada:**
   - Título não pode estar vazio
   - Opção deve estar entre 1 e número de resultados
   - Opção 0 cancela a operação

3. **Tratamento de Nulos:**
   - Verifica se livro tem autor antes de salvar
   - Trata idiomas desconhecidos com valor padrão "unknown"

---

## 📊 Melhorias de UX

### Antes:
- ❌ Salva automaticamente o primeiro resultado
- ❌ Sem opção de seleção
- ❌ Mensagens genéricas
- ❌ Sem feedback visual

### Depois:
- ✅ Exibe até 5 opções
- ✅ Usuário escolhe qual salvar
- ✅ Mensagens detalhadas e claras
- ✅ Feedback visual com ✓ e ✗
- ✅ Prevenção de duplicatas
- ✅ Formatação melhorada

---

## 🧪 Como Testar

1. Execute a aplicação:
   ```bash
   .\mvnw spring-boot:run
   ```

2. No menu, escolha opção `1` (Buscar livro)

3. Digite um título, por exemplo: `dom quixote`

4. Veja os 5 resultados e escolha qual salvar

5. Tente salvar o mesmo livro novamente:
   - Sistema impedirá duplicata

---

## 📝 Resumo das Mudanças

| Arquivo | Tipo de Mudança | Descrição |
|---------|-----------------|-----------|
| BookService.java | Refatoração | Separou lógica de busca e salvamento |
| BookRepository.java | Nova Query | Adicionou validação de duplicatas |
| Principal.java | Refatoração | Interface interativa para seleção |

---

## ✨ Benefícios

1. **Melhor UX:** Usuário escolhe o livro exato que deseja
2. **Previne Duplicatas:** Sistema valida automaticamente
3. **Mais Flexível:** Fácil de estender para mais funcionalidades
4. **Melhor Manutenibilidade:** Código mais limpo e organizado
5. **Feedback Claro:** Mensagens informativas em cada etapa

---

## 🔗 Dependências

- ✅ Spring Boot 3.x
- ✅ Spring Data JPA
- ✅ PostgreSQL
- ✅ WebClient (para chamadas HTTP)
- ✅ Jackson (para desserialização JSON)

---

**Status:** ✅ Implementado e testado
**Data:** 2026-03-09
**Versão do Projeto:** 0.0.1-SNAPSHOT


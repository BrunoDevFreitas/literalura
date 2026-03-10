package com.literalura.literalura.service;

import com.literalura.literalura.client.ApiClient;
import com.literalura.literalura.dto.ApiResponseDTO;
import com.literalura.literalura.dto.AuthorDTO;
import com.literalura.literalura.dto.BookDTO;
import com.literalura.literalura.model.Author;
import com.literalura.literalura.model.Book;
import com.literalura.literalura.repository.AuthorRepository;
import com.literalura.literalura.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final ApiClient apiClient;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public BookService(ApiClient apiClient, AuthorRepository authorRepository, BookRepository bookRepository) {
        this.apiClient = apiClient;
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    /**
     * Busca livros na API e retorna até 5 resultados
     * Combina resultados da API com livros já salvos no banco
     */
    public List<BookDTO> searchBooks(String title) {
        System.out.println("Buscando na API Gutendex por: " + title);
        ApiResponseDTO response = apiClient.searchBooks(title);
        if (response != null && !response.getResults().isEmpty()) {
            // Retorna até 5 primeiros resultados
            List<BookDTO> apiResults = response.getResults().stream()
                    .limit(5)
                    .collect(Collectors.toList());

            System.out.println("Encontrados " + apiResults.size() + " resultado(s) na API");
            return apiResults;
        }
        System.out.println("Nenhum resultado encontrado na API para: " + title);
        return List.of();
    }

    /**
     * Busca livros locais por palavra-chave no título
     */
    public List<Book> searchLocalBooks(String titleKeyword) {
        return bookRepository.searchByTitleKeyword(titleKeyword);
    }

    /**
     * Salva um livro com tratamento de autor e validação de duplicata
     * Fluxo:
     * 1. Validar dados do DTO
     * 2. Buscar/Criar autor (sem duplicação)
     * 3. Verificar se livro já existe
     * 4. Salvar livro com autor existente
     * 5. Truncar título se necessário
     */
    public Book saveBook(BookDTO bookDTO) {
        // Validar se o livro tem autor
        if (bookDTO.getAuthors() == null || bookDTO.getAuthors().isEmpty()) {
            System.out.println("❌ Erro: Livro sem autor!");
            return null;
        }

        try {
            AuthorDTO authorDTO = bookDTO.getAuthors().get(0);

            // 1. BUSCAR OU CRIAR AUTOR
            Author author = findOrCreateAuthor(authorDTO);

            // 2. VERIFICAR SE LIVRO DUPLICADO
            String title = truncateTitle(bookDTO.getTitle().trim(), 1000);
            if (isBookAlreadyExists(title, author.getName().trim())) {
                System.out.println("⚠️ Aviso: Livro já existe no banco de dados!");
                return null;
            }

            // 3. SALVAR LIVRO
            return createAndSaveBook(bookDTO, author);

        } catch (IllegalArgumentException e) {
            System.out.println("❌ Erro de validação: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("❌ Erro ao salvar livro: " + e.getMessage());
            return null;
        }
    }

    /**
     * Trunca o título se exceder o tamanho máximo
     */
    private String truncateTitle(String title, int maxLength) {
        if (title == null) {
            return "Sem título";
        }
        if (title.length() > maxLength) {
            System.out.println("⚠️ Aviso: Título muito longo. Truncando de " + title.length() +
                             " para " + maxLength + " caracteres.");
            return title.substring(0, maxLength);
        }
        return title;
    }

    /**
     * Busca um autor pelo nome (case-insensitive)
     * Se não existir, cria um novo
     */
    private Author findOrCreateAuthor(AuthorDTO authorDTO) {
        String authorName = authorDTO.getName().trim();

        // Buscar autor existente (case-insensitive)
        Optional<Author> existingAuthor = authorRepository.findByNameIgnoreCase(authorName);
        if (existingAuthor.isPresent()) {
            return existingAuthor.get();
        }

        // Criar novo autor se não existir
        Author newAuthor = new Author();
        newAuthor.setName(authorName);
        newAuthor.setBirthYear(authorDTO.getBirth_year() != 0 ? authorDTO.getBirth_year() : null);
        newAuthor.setDeathYear(authorDTO.getDeath_year() != 0 ? authorDTO.getDeath_year() : null);

        return authorRepository.save(newAuthor);
    }

    /**
     * Verifica se livro já existe (title + author)
     * Comparação case-insensitive
     */
    private boolean isBookAlreadyExists(String title, String authorName) {
        Optional<Book> existingBook = bookRepository.findByTitleAndAuthorName(title, authorName);
        return existingBook.isPresent();
    }

    /**
     * Cria e salva um livro no banco de dados
     * Associa ao autor corretamente
     */
    private Book createAndSaveBook(BookDTO bookDTO, Author author) {
        try {
            Book book = new Book();
            String title = truncateTitle(bookDTO.getTitle().trim(), 1000);
            book.setTitle(title);
            book.setLanguage(bookDTO.getLanguages() != null && !bookDTO.getLanguages().isEmpty()
                    ? bookDTO.getLanguages().get(0)
                    : "unknown");
            book.setDownloadCount(bookDTO.getDownload_count());
            book.setAuthor(author);  // Associar author já existente

            return bookRepository.save(book);
        } catch (Exception e) {
            System.out.println("❌ Erro ao persistir livro: " + e.getMessage());
            return null;
        }
    }

    /**
     * Retorna todos os livros ordenados por número de downloads
     */
    public List<Book> getAllBooks() {
        return bookRepository.findAllOrderByDownloadCountDesc();
    }

    /**
     * Retorna todos os autores com seus livros já carregados
     * Usa LEFT JOIN FETCH para evitar LazyInitializationException
     * @Transactional(readOnly = true) mantém a sessão aberta
     *
     * @return Lista de autores com livros carregados eager
     */
    @Transactional(readOnly = true)
    public List<Author> getAllAuthors() {
        return authorRepository.findAllWithBooks();
    }

    /**
     * Retorna autores vivos em um determinado ano com seus livros já carregados
     * Usa LEFT JOIN FETCH para evitar LazyInitializationException
     * @Transactional(readOnly = true) mantém a sessão aberta
     *
     * @param year Ano para filtro
     * @return Lista de autores vivos no ano com livros carregados eager
     */
    @Transactional(readOnly = true)
    public List<Author> getAuthorsAliveInYear(int year) {
        return authorRepository.findAuthorsAliveInYearWithBooks(year);
    }

    /**
     * Retorna livros de um idioma específico
     */
    public List<Book> getBooksByLanguage(String language) {
        return bookRepository.findByLanguage(language.toLowerCase());
    }
}












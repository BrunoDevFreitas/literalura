package com.literalura.literalura.principal;

import com.literalura.literalura.dto.BookDTO;
import com.literalura.literalura.model.Author;
import com.literalura.literalura.model.Book;
import com.literalura.literalura.service.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Principal implements CommandLineRunner {

    private final BookService bookService;
    private final Scanner scanner = new Scanner(System.in);

    public Principal(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            showMenu();
            try {
                // Ler entrada como String e converter para inteiro
                String input = scanner.nextLine().trim();

                // Validar se está vazio
                if (input.isEmpty()) {
                    System.out.println("\n❌ ERRO: Nenhuma entrada detectada!");
                    System.out.println("⚠️  Digite um número entre 0 e 5.\n");
                    continue;
                }

                // Converter para inteiro com tratamento de erro
                int option;
                try {
                    option = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("\n❌ ERRO: '" + input + "' não é um número válido!");
                    System.out.println("⚠️  Digite um número inteiro entre 0 e 5.\n");
                    continue;
                }

                // Validar intervalo
                if (option < 0 || option > 5) {
                    System.out.println("\n❌ ERRO: Opção " + option + " não existe!");
                    System.out.println("⚠️  Opções válidas: 0, 1, 2, 3, 4 ou 5\n");
                    continue;
                }

                switch (option) {
                    case 1:
                        searchBookByTitle();
                        break;
                    case 2:
                        listRegisteredBooks();
                        break;
                    case 3:
                        listAuthors();
                        break;
                    case 4:
                        listAuthorsAliveInYear();
                        break;
                    case 5:
                        listBooksByLanguage();
                        break;
                    case 0:
                        System.out.println("\n👋 Saindo do LiterAlura. Até logo!");
                        return;
                }
            } catch (Exception e) {
                System.out.println("\n❌ ERRO inesperado: " + e.getMessage());
                System.out.println("⚠️  Digite um número inteiro entre 0 e 5.\n");
            }
        }
    }

    private void showMenu() {
        System.out.println("\n=== LiterAlura ===");
        System.out.println("1 - Buscar livro pelo título (API Gutendex)");
        System.out.println("2 - Listar livros registrados");
        System.out.println("3 - Listar autores");
        System.out.println("4 - Listar autores vivos em determinado ano");
        System.out.println("5 - Listar livros por idioma");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private void searchBookByTitle() {
        System.out.print("\nDigite o título do livro: ");
        String title = scanner.nextLine().trim();

        if (title.isEmpty()) {
            System.out.println("\n❌ ERRO: Título não pode estar vazio!");
            System.out.println("⚠️  Digite pelo menos 1 caractere.\n");
            return;
        }

        System.out.println("\n🔍 Buscando na API Gutendex...");
        List<BookDTO> results = bookService.searchBooks(title);

        if (results.isEmpty()) {
            System.out.println("\n❌ Nenhum livro encontrado para: \"" + title + "\"");
            System.out.println("⚠️  Dica: Tente buscar por títulos em inglês ou nomes mais conhecidos.\n");
            return;
        }

        // Exibir até 5 resultados
        System.out.println("\n✓ Encontrados " + results.size() + " resultado(s):\n");
        for (int i = 0; i < results.size(); i++) {
            BookDTO book = results.get(i);
            String authors = book.getAuthors() != null && !book.getAuthors().isEmpty()
                    ? book.getAuthors().get(0).getName()
                    : "Autor desconhecido";
            System.out.println((i + 1) + ". Título: \"" + book.getTitle() + "\"");
            System.out.println("   Autor: " + authors);
            System.out.println("   Downloads: " + book.getDownload_count());
            System.out.println();
        }

        // Pedir ao usuário que escolha
        try {
            System.out.print("Escolha um livro para salvar (1-" + results.size() + ") ou 0 para cancelar: ");

            // Ler entrada como String e converter
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("\n❌ ERRO: Nenhuma entrada detectada!");
                System.out.println("⚠️  Digite um número entre 0 e " + results.size() + "\n");
                return;
            }

            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("\n❌ ERRO: '" + input + "' não é um número válido!");
                System.out.println("⚠️  Digite um número entre 0 e " + results.size() + "\n");
                return;
            }

            // Validar opção de cancelar
            if (choice == 0) {
                System.out.println("\n⛔ Operação cancelada.\n");
                return;
            }

            // Validar intervalo
            if (choice < 1 || choice > results.size()) {
                System.out.println("\n❌ ERRO: Opção " + choice + " não existe!");
                System.out.println("⚠️  Digite um número entre 1 e " + results.size() + "\n");
                return;
            }

            BookDTO selectedBook = results.get(choice - 1);
            System.out.println("\n⏳ Salvando livro na base de dados...");
            Book savedBook = bookService.saveBook(selectedBook);

            if (savedBook != null) {
                System.out.println("\n✅ Livro salvo com sucesso!");
                System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━");
                System.out.println("   Título: " + savedBook.getTitle());
                System.out.println("   Autor: " + savedBook.getAuthor().getName());
                System.out.println("   Idioma: " + savedBook.getLanguage());
                System.out.println("   Downloads: " + savedBook.getDownloadCount());
                System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
            } else {
                System.out.println("\n⚠️  Este livro já existe no banco de dados ou ocorreu um erro ao salvar.\n");
            }
        } catch (Exception e) {
            System.out.println("\n❌ ERRO: Entrada inválida!");
            System.out.println("⚠️  Digite um número inteiro válido.\n");
            scanner.nextLine();
        }
    }

    private void listRegisteredBooks() {
        List<Book> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("\nNenhum livro registrado.");
        } else {
            System.out.println("\n=== Livros Registrados ===\n");
            books.forEach(book -> {
                System.out.println("Título: " + book.getTitle());
                System.out.println("Autor: " + book.getAuthor().getName());
                System.out.println("Idioma: " + book.getLanguage());
                System.out.println("Downloads: " + book.getDownloadCount());
                System.out.println("---");
            });
        }
    }

    private void listAuthors() {
        List<Author> authors = bookService.getAllAuthors();
        if (authors.isEmpty()) {
            System.out.println("\nNenhum autor registrado.");
        } else {
            System.out.println("\n=== Autores Registrados ===\n");
            authors.forEach(author -> {
                System.out.println("Nome: " + author.getName());
                System.out.println("Nascimento: " + (author.getBirthYear() != null ? author.getBirthYear() : "Desconhecido"));
                System.out.println("Falecimento: " + (author.getDeathYear() != null ? author.getDeathYear() : "Vivo"));
                if (author.getBooks() != null && !author.getBooks().isEmpty()) {
                    System.out.println("Livros: " + author.getBooks().stream().map(Book::getTitle).toList());
                }
                System.out.println("---");
            });
        }
    }

    private void listAuthorsAliveInYear() {
        try {
            System.out.print("Digite o ano: ");

            // Ler entrada como String e converter
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("\n❌ ERRO: Nenhuma entrada detectada!");
                System.out.println("⚠️  Digite um número inteiro de 4 dígitos (ex: 1800, 1950, 2000)\n");
                return;
            }

            int year;
            try {
                year = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("\n❌ ERRO: '" + input + "' não é um ano válido!");
                System.out.println("⚠️  Digite um número inteiro de 4 dígitos (ex: 1800, 1950, 2000)\n");
                return;
            }

            // Validar intervalo de ano
            if (year < 1000 || year > 9999) {
                System.out.println("\n❌ ERRO: Ano " + year + " está fora do intervalo válido!");
                System.out.println("⚠️  Digite um ano entre 1000 e 9999\n");
                return;
            }

            List<Author> authors = bookService.getAuthorsAliveInYear(year);
            if (authors.isEmpty()) {
                System.out.println("\n❌ Nenhum autor vivo no ano " + year + "\n");
            } else {
                System.out.println("\n=== Autores Vivos em " + year + " ===\n");
                authors.forEach(author -> {
                    System.out.println("Nome: " + author.getName());
                    System.out.println("Nascimento: " + (author.getBirthYear() != null ? author.getBirthYear() : "Desconhecido"));
                    System.out.println("Falecimento: " + (author.getDeathYear() != null ? author.getDeathYear() : "Vivo"));
                    System.out.println("---");
                });
            }
        } catch (Exception e) {
            System.out.println("\n❌ ERRO: Entrada inválida!");
            System.out.println("⚠️  Digite um número inteiro válido para o ano.\n");
            scanner.nextLine();
        }
    }

    private void listBooksByLanguage() {
        System.out.println("\n📚 Idiomas suportados: PT, EN, FR, ES");
        System.out.print("Digite o idioma (2 letras): ");
        String language = scanner.nextLine().trim().toUpperCase();

        if (language.isEmpty()) {
            System.out.println("\n❌ ERRO: Idioma não pode estar vazio!");
            System.out.println("⚠️  Digite 2 letras (ex: PT, EN, FR, ES)\n");
            return;
        }

        if (language.length() != 2) {
            System.out.println("\n❌ ERRO: '" + language + "' não é um idioma válido!");
            System.out.println("⚠️  Digite exatamente 2 letras (ex: PT, EN, FR, ES)\n");
            return;
        }

        // Validar se contém apenas letras
        if (!language.matches("[A-Z]{2}")) {
            System.out.println("\n❌ ERRO: '" + language + "' contém caracteres inválidos!");
            System.out.println("⚠️  Digite apenas 2 letras (ex: PT, EN, FR, ES)\n");
            return;
        }

        List<Book> books = bookService.getBooksByLanguage(language);
        if (books.isEmpty()) {
            System.out.println("\n❌ Nenhum livro encontrado no idioma " + language + "\n");
        } else {
            System.out.println("\n=== Livros em " + language + " ===\n");
            books.forEach(book -> {
                System.out.println("Título: " + book.getTitle());
                System.out.println("Autor: " + book.getAuthor().getName());
                System.out.println("Downloads: " + book.getDownloadCount());
                System.out.println("---");
            });
        }
    }
}












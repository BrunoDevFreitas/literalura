package com.literalura.literalura.repository;

import com.literalura.literalura.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByName(String name);

    @Query("SELECT a FROM Author a WHERE LOWER(a.name) = LOWER(:name)")
    Optional<Author> findByNameIgnoreCase(@Param("name") String name);

    @Query("SELECT a FROM Author a WHERE a.birthYear <= :year AND (a.deathYear IS NULL OR a.deathYear >= :year)")
    List<Author> findAuthorsAliveInYear(@Param("year") int year);

    /**
     * Busca todos os autores com seus livros usando LEFT JOIN FETCH
     * Evita LazyInitializationException ao acessar author.getBooks()
     * Mantém FetchType.LAZY na entidade mas carrega eager nesta query
     */
    @Query("SELECT DISTINCT a FROM Author a LEFT JOIN FETCH a.books")
    List<Author> findAllWithBooks();

    /**
     * Busca autores vivos em um determinado ano com seus livros usando LEFT JOIN FETCH
     * Evita LazyInitializationException ao acessar author.getBooks()
     * Mantém FetchType.LAZY na entidade mas carrega eager nesta query
     */
    @Query("SELECT DISTINCT a FROM Author a LEFT JOIN FETCH a.books WHERE a.birthYear <= :year AND (a.deathYear IS NULL OR a.deathYear >= :year)")
    List<Author> findAuthorsAliveInYearWithBooks(@Param("year") int year);
}



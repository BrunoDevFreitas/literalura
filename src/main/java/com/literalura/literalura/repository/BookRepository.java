package com.literalura.literalura.repository;

import com.literalura.literalura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByLanguage(String language);

    @Query("SELECT b FROM Book b ORDER BY b.downloadCount DESC")
    List<Book> findAllOrderByDownloadCountDesc();

    @Query("SELECT b FROM Book b WHERE LOWER(b.title) = LOWER(:title) AND LOWER(b.author.name) = LOWER(:authorName)")
    Optional<Book> findByTitleAndAuthorName(@Param("title") String title, @Param("authorName") String authorName);

    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :titleKeyword, '%')) ORDER BY b.downloadCount DESC")
    List<Book> searchByTitleKeyword(@Param("titleKeyword") String titleKeyword);
}



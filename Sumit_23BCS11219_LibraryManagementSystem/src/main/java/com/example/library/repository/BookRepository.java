
package com.example.library.repository;
import com.example.library.entity.Book;
import org.springframework.data.jpa.repository.*;
import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long>{
 @Query("select b from Book b where lower(b.title) like lower(concat('%',:title,'%'))")
 List<Book> searchBooks(String title);
}

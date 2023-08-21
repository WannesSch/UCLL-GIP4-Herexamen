package main.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT COUNT(b) FROM Book b WHERE b.publisher.id = ?1")
    Integer countBooksPerPublisher(long id);

}

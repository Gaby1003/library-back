package library.library.models.books;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query(value = "select * from books where book_id NOT IN (select book_id from inventory)", nativeQuery = true)
    public Optional<List<Book>> getBooks();

    @Query(value = "select * from books where book_id IN (select book_id from inventory where available > 0)", nativeQuery = true)
    public Optional<List<Book>> getBooksAvailable();
}

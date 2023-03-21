package library.library.models.historyBooks;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HistoryBooksRepository extends JpaRepository<HistoryBooks, Integer> {

    @Query(value = "select * from history_books where delivery_date > estimated_delivery_date and user_id=:id", nativeQuery = true)
    public Optional<List<HistoryBooks>> getPenalty(Integer id);

    @Query(value = "select * from history_books where book_id=:bookId and user_id=:userId and borrowing_date=:borrowingDate",
            nativeQuery = true)
    public Optional<HistoryBooks> getById(Integer bookId, Integer userId, LocalDate borrowingDate);
}

package library.library.models.historyBooks;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional
public interface HistoryBooksRepository extends JpaRepository<HistoryBooks, Integer> {

    @Query(value = "select * from history_books where delivery_date > estimated_delivery_date", nativeQuery = true)
    public Optional<List<HistoryBooks>> getPenalty();

    @Query(value = "select * from history_books where book_id=:bookId and user_id=:userId and borrowing_date=:borrowingDate",
            nativeQuery = true)
    public Optional<HistoryBooks> getById(Integer bookId, Integer userId, LocalDate borrowingDate);

    @Modifying
        @Query(value = "INSERT INTO HISTORY_BOOKS (USER_ID,BOOK_ID,BORROWING_DATE,ESTIMATED_DELIVERY_DATE) VALUES (:userId, :bookId,:borrowingDate,:estimatedDeliveryDate)",
            nativeQuery = true)
    public void insert(@Param("bookId") Integer bookId,@Param("userId") Integer userId,
                       @Param("borrowingDate") LocalDate borrowingDate, @Param("estimatedDeliveryDate") LocalDate estimatedDeliveryDate);

    @Modifying
    @Query(value = "UPDATE HISTORY_BOOKS SET delivery_date=:deliveryDate WHERE user_id=:userId and book_id=:bookId " +
            "and borrowing_date=:borrowingDate",
            nativeQuery = true)
    public void update(@Param("bookId") Integer bookId,@Param("userId") Integer userId,
                       @Param("borrowingDate") LocalDate borrowingDate, @Param("deliveryDate") LocalDate deliveryDate);


}

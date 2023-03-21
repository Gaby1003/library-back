package library.library.models.inventory;

import library.library.models.books.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

                @Query(value = "select * from inventory where book_id =:id", nativeQuery = true)
    public Optional<Inventory> getQuantityBooks(Integer id);

}

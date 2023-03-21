package library.library.services;

import library.library.models.books.Book;
import library.library.models.historyBooks.HistoryBooks;
import library.library.models.historyBooks.HistoryBooksPK;
import library.library.models.historyBooks.HistoryBooksRepository;
import library.library.models.inventory.Inventory;
import library.library.models.inventory.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceHistoryBook {
    @Autowired
    private HistoryBooksRepository historyBooksRepository;

    public void addHistoryBook(HistoryBooks historyBooks){
        historyBooksRepository.save(historyBooks);
    }

    public List<HistoryBooks> getPenalty(Integer id){
        Optional<List<HistoryBooks>> penalty = historyBooksRepository.getPenalty(id);
        if(penalty.isPresent()){
            return penalty.get();
        }else {
            throw new RuntimeException("El historial de penalización no se encuentra");
        }
    }

    public List<HistoryBooks> findAllHistory(){
        return historyBooksRepository.findAll();
    }

    public void registerDelivery(HistoryBooks historyBooks) {
        Optional<HistoryBooks> history = historyBooksRepository.getById(historyBooks.getBook().getBookId(),
                historyBooks.getUser().getUserId(), historyBooks.getBorrowingDate());
        history.ifPresentOrElse((value) -> {
            historyBooksRepository.save(history.get().toBuilder()
                    .deliveryDate(historyBooks.getDeliveryDate())
                    .build());
        }, () -> {
            throw new RuntimeException("El registro no se encuentra");
        });
    }


    }

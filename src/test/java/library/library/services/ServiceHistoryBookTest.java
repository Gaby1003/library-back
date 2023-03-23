package library.library.services;

import library.library.models.books.Book;
import library.library.models.historyBooks.HistoryBooks;
import library.library.models.historyBooks.HistoryBooksPK;
import library.library.models.historyBooks.HistoryBooksRepository;
import library.library.models.libraryUsers.LibraryUser;
import library.library.models.libraryUsers.LibraryUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ServiceHistoryBookTest {

    @Mock
    private HistoryBooksRepository historyBooksRepository;

    @InjectMocks
    private ServiceHistoryBook serviceHistoryBook;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        serviceHistoryBook = spy(new ServiceHistoryBook(historyBooksRepository));
    }

    @Test
    void addHistoryBook() {
        LibraryUser user = LibraryUser.builder()
                .userName("Carlos")
                .userLastname("Gomez")
                .documentType("CC")
                .documentNumber("12345678")
                .penalty(false)
                .build();

        Book book = Book.builder().bookName("Cien años de soledad")
                .publicationYear(1967)
                .editorial("Oveja Negra")
                .build();

        HistoryBooks historyBooks = HistoryBooks.builder()
                .book(book)
                .user(user)
                .borrowingDate(LocalDate.now())
                .deliveryDate(LocalDate.parse("2023-03-29"))
                .build();
        serviceHistoryBook.addHistoryBook(historyBooks);
        Mockito.verify(historyBooksRepository, times(1)).insert(historyBooks.getBook().getBookId(),
                historyBooks.getUser().getUserId(),historyBooks.getBorrowingDate(),historyBooks.getEstimatedDeliveryDate());
    }

    @Test
    void updateHistoryBook() {
        LibraryUser user = LibraryUser.builder()
                .userName("Carlos")
                .userLastname("Gomez")
                .documentType("CC")
                .documentNumber("12345678")
                .penalty(false)
                .build();

        Book book = Book.builder().bookName("Cien años de soledad")
                .publicationYear(1967)
                .editorial("Oveja Negra")
                .build();

        HistoryBooks historyBooks = HistoryBooks.builder()
                .book(book)
                .user(user)
                .borrowingDate(LocalDate.now())
                .deliveryDate(LocalDate.parse("2023-03-29"))
                .build();

        HistoryBooks historyBooks1 = historyBooks.toBuilder()
                .deliveryDate(LocalDate.now())
                .build();

        serviceHistoryBook.updateHistoryBook(historyBooks1);
        Mockito.verify(historyBooksRepository, times(1))
                .update(historyBooks1.getBook().getBookId(),
                        historyBooks1.getUser().getUserId(),historyBooks1.getBorrowingDate(),
                        historyBooks1.getDeliveryDate());
    }

    @Test
    void getPenalty() {

        LibraryUser user = LibraryUser.builder()
                .userName("Carlos")
                .userLastname("Gomez")
                .documentType("CC")
                .documentNumber("12345678")
                .penalty(false)
                .build();

        Book book = Book.builder().bookName("Cien años de soledad")
                .publicationYear(1967)
                .editorial("Oveja Negra")
                .build();

        HistoryBooks historyBooks = HistoryBooks.builder()
                .book(book)
                .user(user)
                .borrowingDate(LocalDate.now())
                .estimatedDeliveryDate(LocalDate.now())
                .deliveryDate(LocalDate.parse("2023-03-29"))
                .build();
        serviceHistoryBook.addHistoryBook(historyBooks);
        when(historyBooksRepository.getPenalty()).thenReturn(Optional.of(Collections.singletonList(historyBooks)));
        serviceHistoryBook.getPenalty();
        Mockito.verify(historyBooksRepository, times(1)).getPenalty();

    }

    @Test
    void findAllHistory() {
        LibraryUser user = LibraryUser.builder()
                .userName("Carlos")
                .userLastname("Gomez")
                .documentType("CC")
                .documentNumber("12345678")
                .penalty(false)
                .build();

        Book book = Book.builder().bookName("Cien años de soledad")
                .publicationYear(1967)
                .editorial("Oveja Negra")
                .build();

        HistoryBooks historyBooks = HistoryBooks.builder()
                .book(book)
                .user(user)
                .borrowingDate(LocalDate.now())
                .estimatedDeliveryDate(LocalDate.now())
                .deliveryDate(LocalDate.parse("2023-03-29"))
                .build();

        when(historyBooksRepository.findAll()).thenReturn(Collections.singletonList(historyBooks));
        List<HistoryBooks> historyBooksList = serviceHistoryBook.findAllHistory();
        Assertions.assertTrue(historyBooksList.size() == 1);
    }

    @Test
    void getBook() {
        LibraryUser user = LibraryUser.builder()
                .userName("Carlos")
                .userLastname("Gomez")
                .documentType("CC")
                .documentNumber("12345678")
                .penalty(false)
                .build();

        Book book = Book.builder().bookName("Cien años de soledad")
                .publicationYear(1967)
                .editorial("Oveja Negra")
                .build();

        HistoryBooks historyBooks = HistoryBooks.builder()
                .book(book)
                .user(user)
                .borrowingDate(LocalDate.now())
                .estimatedDeliveryDate(LocalDate.now())
                .deliveryDate(LocalDate.parse("2023-03-29"))
                .build();
        serviceHistoryBook.addHistoryBook(historyBooks);

        when(historyBooksRepository.getById(anyInt(), anyInt(), any(LocalDate.class)))
                .thenReturn(Optional.of(historyBooks));
        HistoryBooks result = serviceHistoryBook.getBook(book.getBookId(),
                user.getUserId(), historyBooks.getBorrowingDate());
        Assertions.assertTrue(result.getUser().getUserName().equals("Carlos"));
    }
}
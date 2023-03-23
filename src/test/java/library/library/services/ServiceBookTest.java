package library.library.services;

import library.library.models.books.Book;
import library.library.models.books.BookRepository;
import library.library.models.inventory.Inventory;
import library.library.models.inventory.InventoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class ServiceBookTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private ServiceBook serviceBook;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        serviceBook = spy(new ServiceBook(bookRepository, inventoryRepository));
    }

    @Test
    void addBook() {
        Book book = Book.builder().bookName("Cien años de soledad")
                .publicationYear(1967)
                .editorial("Oveja Negra")
                .build();
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        serviceBook.addBook(book);
        Mockito.verify(bookRepository, times(1)).save(book);
    }

    @Test
    void findAllBooks() {
        Book book = Book.builder().bookName("Cien años de soledad")
                .publicationYear(1967)
                .editorial("Oveja Negra")
                .build();

        when(bookRepository.findAll()).thenReturn(Collections.singletonList(book));
        List<Book> books = serviceBook.findAllBooks();
        Assertions.assertTrue(books.size() == 1);
    }

    @Test
    void getBook() {
        Book book = Book.builder()
                .bookId(100)
                .bookName("Cien años de soledad")
                .publicationYear(1967)
                .editorial("Oveja Negra")
                .build();

        when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book));
        Book result = serviceBook.getBook(100);
        Assertions.assertTrue(result.getBookName().equals("Cien años de soledad"));
    }

    @Test
    void getBooks() {
        Book book = Book.builder()
                .bookName("Cien años de soledad")
                .publicationYear(1967)
                .editorial("Oveja Negra")
                .build();

        when(bookRepository.getBooks()).thenReturn(Optional.of(Collections.singletonList(book)));
        List<Book > result = serviceBook.getBooks();
        Assertions.assertTrue(result.size() == 1);
    }

    @Test
    void getBooksAvailable() {
        Book book = Book.builder()
                .bookName("Cien años de soledad")
                .publicationYear(1967)
                .editorial("Oveja Negra")
                .build();

        when(bookRepository.save(any(Book.class))).thenReturn(book);
        serviceBook.addBook(book);

        when(bookRepository.getBooksAvailable()).thenReturn(Optional.of(Collections.singletonList(book)));
        List<Book > result = serviceBook.getBooksAvailable();
        Assertions.assertTrue(result.size() == 1);
    }

    @Test
    void updateBook() {
        Book book = Book.builder().bookName("Cien años de soledad")
                .publicationYear(1967)
                .editorial("Oveja Negra")
                .build();

        when(bookRepository.save(any(Book.class))).thenReturn(book.toBuilder().bookName("Libro").build());
        when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book));

        serviceBook.updateBook(book.toBuilder().bookName("Libro").build());
        Mockito.verify(bookRepository, times(1)).save(book.toBuilder().bookName("Libro").build());
    }

    @Test
    void deleteBook() {
        Book book = Book.builder().bookName("Cien años de soledad")
                .publicationYear(1967)
                .editorial("Oveja Negra")
                .build();
        when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book));

        serviceBook.deleteBook(book.getBookId());
        Mockito.verify(bookRepository, times(1)).deleteById(book.getBookId());
    }
}
package library.library.services;

import library.library.models.books.Book;
import library.library.models.books.BookRepository;
import library.library.models.inventory.Inventory;
import library.library.models.inventory.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceBook {

    private final BookRepository bookRepository;

    private final InventoryRepository inventoryRepository;

    public void addBook(Book book){
        bookRepository.save(book);
    }

    public List<Book> findAllBooks(){
        return bookRepository.findAll();
    }

    public Book getBook(Integer id){
        return  bookRepository.findById(id).get();
    }

    public List<Book> getBooks(){
        Optional<List<Book>> inventory = bookRepository.getBooks();
        if(inventory.isPresent()){
            return bookRepository.getBooks().get();
        } else {
            throw new RuntimeException("El inventario del libro no se encuentra");
        }
    }

    public List<Book> getBooksAvailable(){
        Optional<List<Book>> inventory = bookRepository.getBooksAvailable();
        if(inventory.isPresent()){
            return inventory.get();
        } else {
            throw new RuntimeException("El inventario del libro no se encuentra");
        }
    }

    public void updateBook(Book bookRequest){
        Optional<Book> book = bookRepository.findById(bookRequest.getBookId());
        book.ifPresentOrElse((value) -> {
            bookRepository.save(book.get().toBuilder()
                    .bookName(bookRequest.getBookName())
                    .editorial(bookRequest.getEditorial())
                    .publicationYear(bookRequest.getPublicationYear())
                    .build());
        }, () -> {
            throw new RuntimeException("El libro no se encuentra");
        });
    }

    public void deleteBook(int id){
        Optional<Book> book = bookRepository.findById(id);
        book.ifPresentOrElse((value) -> {
            Optional<Inventory> inventory = inventoryRepository.getQuantityBooks(id);
            if (inventory.isPresent()){
                inventoryRepository.deleteById(inventory.get().getInventoryId());
            }
            bookRepository.deleteById(id);
        },  () -> {
            throw new RuntimeException("El libro no se encuentra");
        });
    }
}

package library.library.services;

import library.library.models.books.Book;
import library.library.models.books.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceBook {

    @Autowired
    private BookRepository bookRepository;

    public void addBook(Book book){
        bookRepository.save(book);
    }

    public List<Book> findAllBooks(){
        return bookRepository.findAll();
    }

    public Book getBook(Integer id){
        return  bookRepository.findById(id).get();
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
            bookRepository.deleteById(id);
        },  () -> {
            throw new RuntimeException("El libro no se encuentra");
        });
    }
}

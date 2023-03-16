package library.library.controllers;

import library.library.services.ServiceBook;
import library.library.books.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*@CrossOrigin(origins = "http://localhost:4200")*/
@CrossOrigin(exposedHeaders = {"Access-Control-Allow-Origin","Access-Control-Allow-Credentials"})
@RestController
@RequestMapping("/books")
public class ControllerBook {
    @Autowired
    private ServiceBook serviceBook;

    @PostMapping("/add")
    public ResponseEntity<?> addBook(@RequestBody Book book){
        try{
            serviceBook.addBook(book);
            return ResponseEntity.ok("Libro agregado");
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get")
    public List<Book> getBooks(){
        return serviceBook.findAllBooks();
    }

    @GetMapping("/getBook")
    public Book getBook(@RequestParam(name = "id") Integer id){
        return serviceBook.getBook(id);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateBook(@RequestBody Book book){
        try{
            serviceBook.updateBook(book);
            return ResponseEntity.ok("Libro actualizado");
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteBook(@RequestParam(name = "id") Integer id){
        try{
            serviceBook.deleteBook(id);
            return ResponseEntity.ok("Libro eliminado");
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

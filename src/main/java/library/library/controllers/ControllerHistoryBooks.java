package library.library.controllers;

import library.library.models.historyBooks.HistoryBooks;
import library.library.models.libraryUsers.LibraryUser;
import library.library.services.ServiceHistoryBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(exposedHeaders = {"Access-Control-Allow-Origin","Access-Control-Allow-Credentials"})
@RestController
@RequestMapping("/history-books")
public class ControllerHistoryBooks {
    @Autowired
    private ServiceHistoryBook serviceHistoryBook;

    @PostMapping("/add")
    public ResponseEntity<?> addLoan(@RequestBody HistoryBooks historyBooks){
        try{
            serviceHistoryBook.addHistoryBook(historyBooks);
            return ResponseEntity.ok().build();
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get")
    public List<HistoryBooks> addLoan(){
        return serviceHistoryBook.findAllHistory();
    }

    @GetMapping("/getPenalty")
    public List<HistoryBooks> addPenalty(@RequestParam(name = "id") Integer id){
        return serviceHistoryBook.getPenalty(id);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateHistory(@RequestBody HistoryBooks historyBook){
        try{
            serviceHistoryBook.registerDelivery(historyBook);
            return ResponseEntity.ok().build();
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}

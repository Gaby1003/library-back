package library.library.controllers;

import library.library.models.historyBooks.HistoryBooks;
import library.library.models.inventory.Inventory;
import library.library.models.libraryUsers.LibraryUser;
import library.library.services.ServiceHistoryBook;
import library.library.services.ServiceInventory;
import library.library.services.ServiceLibraryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(exposedHeaders = {"Access-Control-Allow-Origin","Access-Control-Allow-Credentials"})
@RestController
@RequestMapping("/history-books")
public class ControllerHistoryBooks {
    @Autowired
    private ServiceHistoryBook serviceHistoryBook;
    @Autowired
    private ServiceInventory serviceInventory;

    @Autowired
    private ServiceLibraryUser serviceLibraryUser;

    @PostMapping("/add")
    public ResponseEntity<?> addLoan(@RequestBody HistoryBooks historyBooks){
        try{
            Inventory inventory = serviceInventory.getQuantityBooks(historyBooks.getBook().getBookId());

            inventory.setAvailable(inventory.getAvailable()-1);
            serviceInventory.updateQuantity(inventory);
            serviceHistoryBook.addHistoryBook(historyBooks);
            System.out.println(inventory.toString());

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
    public List<HistoryBooks> addPenalty(){
        return serviceHistoryBook.getPenalty();
    }



    @PutMapping("/update")
    public ResponseEntity<?> updateHistory(@RequestBody HistoryBooks historyBook){
        try{
            Inventory inventory = serviceInventory.getQuantityBooks(historyBook.getBook().getBookId());
            inventory.setAvailable(inventory.getAvailable()+1);
            serviceInventory.updateQuantity(inventory);
            if(historyBook.getDeliveryDate().isAfter(historyBook.getEstimatedDeliveryDate())){
                serviceLibraryUser.updateUser(historyBook.getUser().toBuilder().penalty(true).build());
            }
            serviceHistoryBook.updateHistoryBook(historyBook);
            return ResponseEntity.ok().build();
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getHistory")
    public HistoryBooks getById(@RequestParam(name = "userId") Integer userId, @RequestParam(name = "bookId") Integer bookId,
                                      @RequestParam(name = "borrowingDate") String   borrowingDate){
        return serviceHistoryBook.getBook(bookId, userId, LocalDate.parse(borrowingDate));
    }


}

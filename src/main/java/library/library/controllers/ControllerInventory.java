package library.library.controllers;

import library.library.models.books.Book;
import library.library.models.inventory.Inventory;
import library.library.services.ServiceInventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(exposedHeaders = {"Access-Control-Allow-Origin","Access-Control-Allow-Credentials"})
@RestController
@RequestMapping("/inventory")
public class ControllerInventory {
    @Autowired
    private ServiceInventory serviceInventory;
    @PostMapping("/add")
    public ResponseEntity<?> addInventory(@RequestBody Inventory inventory){
        try{
            serviceInventory.addInventory(inventory);
            return ResponseEntity.ok().build();
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get")
    public List<Inventory> getInventories(){
        return serviceInventory.findAllInventories();
    }

    @GetMapping("/getQuantityBooks")
    public Inventory getQuantityBooks(@RequestParam(name = "id") Integer id){
        return serviceInventory.getQuantityBooks(id);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateQuantity(@RequestBody Inventory inventory){
        try{
            serviceInventory.updateQuantity(inventory);
            return ResponseEntity.ok().build();
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteInventory(@RequestParam(name = "id") Integer id){
        try{
            serviceInventory.deleteInventory(id);
            return ResponseEntity.ok().build();
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

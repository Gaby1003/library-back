package library.library.services;

import library.library.models.books.Book;
import library.library.models.inventory.Inventory;
import library.library.models.inventory.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceInventory {

    @Autowired
    private InventoryRepository inventoryRepository;

    public void addInventory(Inventory inventory){
        inventoryRepository.save(inventory);
    }

    public List<Inventory> findAllInventories(){
        return inventoryRepository.findAll();
    }

    public Inventory getQuantityBooks(Integer id){
        return inventoryRepository.getQuantityBooks(id).get();
    }

    public void updateQuantity(Inventory inventoryRequest){
        Optional<Inventory> inventory = inventoryRepository.getQuantityBooks(inventoryRequest.getBook().getBookId());
        System.out.println(inventoryRequest.toString() + "service update");
        inventory.ifPresentOrElse((value) -> {
            inventoryRepository.save(inventory.get().toBuilder()
                    .quantity(inventoryRequest.getQuantity())
                    .available(inventoryRequest.getAvailable())
                    .build());
        }, () -> {
            throw new RuntimeException("El inventario del libro no se encuentra");
        });
    }

    public void deleteInventory(int id){
        Optional<Inventory> inventory = inventoryRepository.findById(id);
        inventory.ifPresentOrElse((value) -> {
            inventoryRepository.deleteById(id);
        },  () -> {
            throw new RuntimeException("El inventario del libro no se encuentra");
        });
    }
}

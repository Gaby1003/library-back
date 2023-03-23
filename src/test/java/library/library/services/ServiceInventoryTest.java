package library.library.services;

import library.library.models.books.Book;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ServiceInventoryTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private ServiceInventory serviceInventory;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        serviceInventory = spy(new ServiceInventory( inventoryRepository));
    }

    @Test
    void addInventory() {
        Book book = Book.builder().bookName("Cien años de soledad")
                .publicationYear(1967)
                .editorial("Oveja Negra")
                .build();
        Inventory inventory = Inventory.builder()
                .book(book)
                .available(10)
                .quantity(10)
                .build();
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(inventory);
        serviceInventory.addInventory(inventory);
        Mockito.verify(inventoryRepository, times(1)).save(inventory);
    }

    @Test
    void findAllInventories() {
        Book book = Book.builder().bookName("Cien años de soledad")
                .publicationYear(1967)
                .editorial("Oveja Negra")
                .build();
        Inventory inventory = Inventory.builder()
                .book(book)
                .available(10)
                .quantity(10)
                .build();

        when(inventoryRepository.findAll()).thenReturn(Collections.singletonList(inventory));
        List<Inventory> inventories = serviceInventory.findAllInventories();
        Assertions.assertTrue(inventories.size() == 1);
    }

    @Test
    void getQuantityBooks() {

        Book book = Book.builder().bookName("Cien años de soledad")
                .publicationYear(1967)
                .editorial("Oveja Negra")
                .build();
        Inventory inventory = Inventory.builder()
                .inventoryId(100)
                .book(book)
                .available(10)
                .quantity(10)
                .build();

        when(inventoryRepository.getQuantityBooks(anyInt())).thenReturn(Optional.of(inventory));
        Inventory result = serviceInventory.getQuantityBooks(100);
        Assertions.assertTrue(result.getBook().getBookName().equals("Cien años de soledad"));
    }

    @Test
    void updateQuantity() {
        Book book = Book.builder().bookName("Cien años de soledad")
                .publicationYear(1967)
                .editorial("Oveja Negra")
                .build();
        Inventory inventory = Inventory.builder()
                .inventoryId(100)
                .book(book)
                .available(10)
                .quantity(10)
                .build();

        when(inventoryRepository.save(any(Inventory.class))).thenReturn(
                inventory.toBuilder()
                        .available(8)
                        .build());

        when(inventoryRepository.getQuantityBooks(anyInt())).thenReturn(Optional.of(inventory));

        serviceInventory.updateQuantity(inventory.toBuilder()
                .available(8)
                .build());
        Mockito.verify(inventoryRepository, times(1)).save(inventory.toBuilder()
                .available(8)
                .build());
    }

    @Test
    void deleteInventory() {
        Book book = Book.builder().bookName("Cien años de soledad")
                .publicationYear(1967)
                .editorial("Oveja Negra")
                .build();
        Inventory inventory = Inventory.builder()
                .inventoryId(100)
                .book(book)
                .available(10)
                .quantity(10)
                .build();

        when(inventoryRepository.findById(anyInt())).thenReturn(Optional.of(inventory));

        serviceInventory.deleteInventory(inventory.getInventoryId());
        Mockito.verify(inventoryRepository, times(1)).deleteById(inventory.getInventoryId());
    }
}
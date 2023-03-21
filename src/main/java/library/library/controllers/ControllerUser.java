package library.library.controllers;

import library.library.models.books.Book;
import library.library.models.libraryUsers.LibraryUser;
import library.library.services.ServiceLibraryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(exposedHeaders = {"Access-Control-Allow-Origin","Access-Control-Allow-Credentials"})
@RestController
@RequestMapping("/users")
public class ControllerUser {

    @Autowired
    private ServiceLibraryUser serviceLibraryUser;

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody LibraryUser user){
        try{
                serviceLibraryUser.addUser(user);
                return ResponseEntity.ok().build();
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get")
    public List<LibraryUser> getUsers(){
        return serviceLibraryUser.findAllUsers();
    }

    @GetMapping("/getUser")
    public LibraryUser getUser(@RequestParam(name = "id") Integer id){
        return serviceLibraryUser.getUser(id);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody LibraryUser user){
        try{
            serviceLibraryUser.updateUser(user);
            return ResponseEntity.ok().build();
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam(name = "id") Integer id){
        try{
            serviceLibraryUser.deleteBook(id);
            return ResponseEntity.ok().build();
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

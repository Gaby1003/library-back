package library.library.services;

import library.library.models.libraryUsers.LibraryUser;
import library.library.models.libraryUsers.LibraryUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceLibraryUser {

    private final LibraryUserRepository libraryUserRepository;


    public void addUser(LibraryUser libraryUser){
        libraryUserRepository.save(libraryUser);
    }

    public List<LibraryUser> findAllUsers(){
        return libraryUserRepository.findAll();
    }

    public LibraryUser getUser(Integer id){
        return  libraryUserRepository.findById(id).get();
    }

    public void updateUser(LibraryUser userRequest){
        Optional<LibraryUser> user = libraryUserRepository.findById(userRequest.getUserId());
        user.ifPresentOrElse((value) -> {
            libraryUserRepository.save(user.get().toBuilder()
                    .userName(userRequest.getUserName())
                    .userLastname(userRequest.getUserLastname())
                    .documentNumber(userRequest.getDocumentNumber())
                    .documentType(userRequest.getDocumentType())
                    .penalty(userRequest.isPenalty())
                    .build());
        }, () -> {
            throw new RuntimeException("El usuario no se encuentra");
        });
    }

    public void deleteUser(int id){
        Optional<LibraryUser> user = libraryUserRepository.findById(id);
        user.ifPresentOrElse((value) -> {
            libraryUserRepository.deleteById(id);
        },  () -> {
            throw new RuntimeException("El usuario no se encuentra");
        });
    }
}

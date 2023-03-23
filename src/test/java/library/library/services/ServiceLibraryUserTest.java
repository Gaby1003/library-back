package library.library.services;

import library.library.models.libraryUsers.LibraryUser;
import library.library.models.libraryUsers.LibraryUserRepository;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ServiceLibraryUserTest {

    @Mock
    private LibraryUserRepository libraryUserRepository;

    @InjectMocks
    private ServiceLibraryUser serviceLibraryUser;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        serviceLibraryUser = spy(new ServiceLibraryUser( libraryUserRepository));
    }

    @Test
    void addUser() {
        LibraryUser user = LibraryUser.builder()
                .userName("Carlos")
                .userLastname("Gomez")
                .documentType("CC")
                .documentNumber("12345678")
                .penalty(false)
                .build();

        when(libraryUserRepository.save(any(LibraryUser.class))).thenReturn(user);
        serviceLibraryUser.addUser(user);
        Mockito.verify(libraryUserRepository, times(1)).save(user);
    }

    @Test
    void findAllUsers() {
        LibraryUser user = LibraryUser.builder()
                .userName("Carlos")
                .userLastname("Gomez")
                .documentType("CC")
                .documentNumber("12345678")
                .penalty(false)
                .build();

        when(libraryUserRepository.findAll()).thenReturn(Collections.singletonList(user));
        List<LibraryUser> users = serviceLibraryUser.findAllUsers();
        Assertions.assertTrue(users.size() == 1);
    }

    @Test
    void getUser() {
        LibraryUser user = LibraryUser.builder()
                .userId(100)
                .userName("Carlos")
                .userLastname("Gomez")
                .documentType("CC")
                .documentNumber("12345678")
                .penalty(false)
                .build();

        when(libraryUserRepository.findById(anyInt())).thenReturn(Optional.of(user));
        LibraryUser result = serviceLibraryUser.getUser(100);
        Assertions.assertTrue(result.getUserName().equals("Carlos"));
    }

    @Test
    void updateUser() {
        LibraryUser user = LibraryUser.builder()
                .userId(100)
                .userName("Carlos")
                .userLastname("Gomez")
                .documentType("CC")
                .documentNumber("12345678")
                .penalty(false)
                .build();

        when(libraryUserRepository.save(any(LibraryUser.class))).thenReturn(user.toBuilder()
                .userLastname("Perez").build());
        when(libraryUserRepository.findById(anyInt())).thenReturn(Optional.of(user));

        serviceLibraryUser.updateUser(user.toBuilder().userLastname("Perez").build());
        Mockito.verify(libraryUserRepository, times(1)).save(
                user.toBuilder().userLastname("Perez").build());

    }

    @Test
    void deleteBook() {
        LibraryUser user = LibraryUser.builder()
                .userId(100)
                .userName("Carlos")
                .userLastname("Gomez")
                .documentType("CC")
                .documentNumber("12345678")
                .penalty(false)
                .build();

        when(libraryUserRepository.findById(anyInt())).thenReturn(Optional.of(user));

        serviceLibraryUser.deleteUser(user.getUserId());
        Mockito.verify(libraryUserRepository, times(1)).deleteById(
                user.getUserId());

    }
}
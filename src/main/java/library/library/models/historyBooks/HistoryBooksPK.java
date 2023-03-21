package library.library.models.historyBooks;


import library.library.models.books.Book;
import library.library.models.libraryUsers.LibraryUser;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class HistoryBooksPK implements Serializable {
    private LibraryUser user;

    private Book book;
    private LocalDate borrowingDate;


}

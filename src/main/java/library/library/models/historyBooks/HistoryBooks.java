package library.library.models.historyBooks;


import library.library.models.books.Book;
import library.library.models.libraryUsers.LibraryUser;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "history_books")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(HistoryBooksPK.class)
@Builder(toBuilder = true)
@ToString
public class HistoryBooks {

    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "user_id",  nullable = false)
    @Id
    private LibraryUser user;

    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "book_id",  nullable = false)
    @Id
    private Book book;

    @Id
    @Column(name = "borrowing_date")
    private LocalDate borrowingDate;

    /*@EmbeddedId
    private HistoryBooksPK id;*/

    @Column(name = "estimated_delivery_date")
    private LocalDate estimatedDeliveryDate;


    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

}

package library.library.books;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "books")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int bookId;

    @Column(name = "book_name")
    private String bookName;

    @Column(name = "editorial")
    private String editorial;

    @Column(name = "publication_year")
    private int publicationYear;

}

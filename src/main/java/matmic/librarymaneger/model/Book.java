package matmic.librarymaneger.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import matmic.librarymaneger.model.enums.Availability;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"bookLoan"})
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String author;
    private Integer publishYear;
    private String publisher;
    private String title;
    private String isbn;
    private Integer releaseNumber;
    private String genre;

    @Enumerated(value= EnumType.STRING)
    private Availability isAvailable;

    @Lob
    private Byte[] coverImage;

    @OneToOne(cascade = CascadeType.ALL)
    private BookLoan bookLoan;

    public Book(){
        this.isAvailable = Availability.AVAILABLE;
    }


}

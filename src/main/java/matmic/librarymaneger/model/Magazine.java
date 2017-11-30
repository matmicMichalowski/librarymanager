package matmic.librarymaneger.model;

import lombok.Data;
import matmic.librarymaneger.model.enums.Availability;

import javax.persistence.*;

@Data
@Entity
public class Magazine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String issn;
    private String publisher;
    private Integer magazineNumber;
    private Integer year;
    private String genre;
    private Availability isAvailable;



    @OneToOne
    private MagazineLoan magazineLoan;

}

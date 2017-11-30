package matmic.librarymaneger.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import matmic.librarymaneger.model.enums.Availability;
import matmic.librarymaneger.model.enums.DistributionType;
import matmic.librarymaneger.model.enums.ItemType;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private ItemType itemType;

    @Enumerated(value = EnumType.STRING)
    private DistributionType distributionType;

    private String internationalSegregationNumber;

    private String title;

    private String publisher;

    private String releaseNumber;

    private String author;

    private String genre;

    private int year;

    @Enumerated(value = EnumType.STRING)
    private Availability isAvailable;

    @Lob
    private Byte[] itemImage;

    @OneToOne
    private Loan loan;


}

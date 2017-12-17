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
public class Item extends ImageSuperclass{

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
    private Availability isAvailable = Availability.AVAILABLE;


    @OneToOne(cascade = CascadeType.ALL)
    private Loan loan;

    public void setLoan(Loan loan){

        if (sameAsFormerLoan(loan)){
            return;
        }
        Loan actualLoan = this.loan;
        this.loan = loan;
        this.setIsAvailable(Availability.BORROWED);

        if(actualLoan != null){
            actualLoan.setItem(null);
        }
        if(loan != null){
            loan.setItem(this);
        }
    }

    private boolean sameAsFormerLoan(Loan newLoan){
        return loan == null ? newLoan == null : loan.equals(newLoan);
    }

}

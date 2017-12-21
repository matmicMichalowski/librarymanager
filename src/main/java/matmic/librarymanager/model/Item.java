package matmic.librarymanager.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import matmic.librarymanager.model.enums.Availability;
import matmic.librarymanager.model.enums.DistributionType;
import matmic.librarymanager.model.enums.ItemType;

import javax.persistence.*;
import javax.validation.constraints.*;

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

    @NotEmpty
    @Pattern(regexp = "^([0-9]{9,12}-)[0-9xX]$|[0-9]{4}-[0-9]{3}[0-9xX]$", message = "Valid way to provide segregation number for ISBN: 123456789-x, and for ISSN 1234-123x (x can also be a digit).")
    private String internationalSegregationNumber;

    @Size(min=2, max=600)
    @NotEmpty(message = "Please provide item Title")
    private String title;

    @Size(min=2, max=50)
    @NotEmpty(message = "Please provide item Publisher")
    private String publisher;

    @NotEmpty
    @Pattern(regexp = "^[0-9]{1,4}$|[0-9]{1,4}/[0-9]{2,4}$", message = "Valid way to provide release number is: digit or \"value/value\"")
    private String releaseNumber;

    @NotEmpty
    @Size(min=2, max = 55)
    private String author;

    @NotEmpty
    @Size(min = 2, max = 20)
    private String genre;

    @NotNull
    @Min(1)
    @Max(2100)
    private Integer year;

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

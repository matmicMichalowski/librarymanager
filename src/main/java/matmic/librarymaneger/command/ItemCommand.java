package matmic.librarymaneger.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import matmic.librarymaneger.model.enums.DistributionType;
import matmic.librarymaneger.model.enums.ItemType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
public class ItemCommand {

    private Long id;

    private ItemType itemType;
    private DistributionType distributionType;

    @NotEmpty(message = "Please provide item ISN")
    private String internationalSegregationNumber;

    @Size(min=2, max=600)
    @NotEmpty(message = "Please provide item Title")
    private String title;

    @Size(min=2, max=50)
    @NotEmpty(message = "Please provide item Publisher")
    private String publisher;


    @NotEmpty(message = "Please provide item Release Number")
    private String releaseNumber;

    @NotEmpty(message = "Please provide item Author")
    private String author;

    @NotEmpty(message = "Please provide item Genre")
    private String genre;

    @NotEmpty(message = "Please provide item Year")
    private int year;
}

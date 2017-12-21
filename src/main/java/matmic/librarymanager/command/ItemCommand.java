package matmic.librarymanager.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import matmic.librarymanager.model.enums.DistributionType;
import matmic.librarymanager.model.enums.ItemType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
public class ItemCommand {

    private Long id;

    private ItemType itemType;
    private DistributionType distributionType;

    @NotEmpty(message = "Please provide item ISN")
    @Pattern(regexp = "^([0-9]{9,12}-)[0-9xX]$")
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
    @Size(min = 2, max = 64)
    private String author;

    @NotEmpty(message = "Please provide item Genre")
    @Size(min = 4, max = 36)
    private String genre;

    @NotNull(message = "Please provide item Year")
    private int year;
}

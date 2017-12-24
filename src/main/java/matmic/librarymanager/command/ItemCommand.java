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


    @Pattern(regexp = "^([0-9]{9,12}-)[0-9xX]$", message = "{pattern.isn}")
    private String internationalSegregationNumber;

    @Size(min=2, max=600)
    @NotEmpty(message = "{required}")
    private String title;

    @Size(min=2, max=50)
    @NotEmpty(message = "{required}")
    private String publisher;



    @Pattern(regexp = "^[0-9]{1,4}$|[0-9]{1,4}/[0-9]{2,4}$", message ="{item.release}")
    private String releaseNumber;

    @NotEmpty(message = "{required}")
    @Size(min = 2, max = 64)
    private String author;

    @NotEmpty(message = "{required}")
    @Size(min = 4, max = 36)
    private String genre;

    @NotNull
    private int year;
}

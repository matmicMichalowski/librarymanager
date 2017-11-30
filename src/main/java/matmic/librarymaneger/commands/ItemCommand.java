package matmic.librarymaneger.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import matmic.librarymaneger.model.enums.Availability;
import matmic.librarymaneger.model.enums.DistributionType;
import matmic.librarymaneger.model.enums.ItemType;


@Getter
@Setter
@NoArgsConstructor
public class ItemCommand {

    private Long id;
    private ItemType itemType;
    private DistributionType distributionType;
    private String internationalSegregationNumber;
    private String title;
    private String publisher;
    private String releaseNumber;
    private String author;
    private String genre;
    private int year;
    private Availability isAvailable;
    private Byte[] itemImage;
    private Long loanId;
}

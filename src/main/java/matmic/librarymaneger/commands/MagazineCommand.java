package matmic.librarymaneger.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import matmic.librarymaneger.model.Availability;


@Getter
@Setter
@NoArgsConstructor
public class MagazineCommand {
    private Long id;
    private String title;
    private String issn;
    private String publisher;
    private Integer magazineNumber;
    private Integer year;
    private String genre;
    private Availability isAvailable;
}

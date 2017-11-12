package matmic.librarymaneger.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import matmic.librarymaneger.model.Availability;


@Getter
@Setter
@NoArgsConstructor
public class BookCommand {
    private Long id;
    private String author;
    private Integer publishYear;
    private String publisher;
    private String title;
    private String isbn;
    private Integer releaseNumber;
    private String genre;
    private Availability isAvailable;
}

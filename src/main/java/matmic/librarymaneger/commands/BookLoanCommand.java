package matmic.librarymaneger.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookLoanCommand {
    private Long id;
    private String loanDate;
    private BookCommand book;
}

package matmic.librarymaneger.commands;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import matmic.librarymaneger.model.User;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class LibraryAccountCommand {
    private Long id;
    private User user;
    private Set<MagazineLoanCommand> magazineLoans = new HashSet<>();
    private Set<BookLoanCommand> bookLoans = new HashSet<>();
}

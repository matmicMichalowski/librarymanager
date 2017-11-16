package matmic.librarymaneger.converters;

import lombok.Synchronized;
import matmic.librarymaneger.commands.LibraryAccountCommand;
import matmic.librarymaneger.model.LibraryAccount;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class LibraryAccountToLibraryAccountCommand implements Converter<LibraryAccount, LibraryAccountCommand>{

    private final BookLoanToBookLoanCommand bookLoanConverter;
    private final MagazineLoanToMagazineLoanCommand magazineLoanConverter;

    public LibraryAccountToLibraryAccountCommand(BookLoanToBookLoanCommand bookLoanConverter, MagazineLoanToMagazineLoanCommand magazineLoanToMagazineLoanCommand) {
        this.bookLoanConverter = bookLoanConverter;
        this.magazineLoanConverter = magazineLoanToMagazineLoanCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public LibraryAccountCommand convert(LibraryAccount loanLine) {
        if(loanLine == null){
            return null;
        }

        final LibraryAccountCommand libraryAccountCommand = new LibraryAccountCommand();

        libraryAccountCommand.setId(loanLine.getId());
        libraryAccountCommand.setUser(loanLine.getUser());

        if(loanLine.getBookLoans() != null && loanLine.getBookLoans().size() > 0){
            loanLine.getBookLoans().forEach( bookLoan -> libraryAccountCommand.getBookLoans().add(bookLoanConverter.convert(bookLoan)));
        }
        if(loanLine.getMagazineLoans() != null && loanLine.getMagazineLoans().size() > 0){
            loanLine.getMagazineLoans().forEach( magazineLoan -> libraryAccountCommand.getMagazineLoans().add(magazineLoanConverter.convert(magazineLoan)));
        }

        return libraryAccountCommand;
    }
}

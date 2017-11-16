package matmic.librarymaneger.converters;

import lombok.Synchronized;
import matmic.librarymaneger.commands.LibraryAccountCommand;
import matmic.librarymaneger.model.LibraryAccount;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class LibraryAccountCommandToLibraryAccount implements Converter<LibraryAccountCommand, LibraryAccount>{

    private final BookLoanCommandToBookLoan bookLoanConverter;
    private final MagazineLoanCommandToMagazineLoan magazineLoanConverter;

    public LibraryAccountCommandToLibraryAccount(BookLoanCommandToBookLoan bookLoanConverter, MagazineLoanCommandToMagazineLoan magazineLoanConverter) {
        this.bookLoanConverter = bookLoanConverter;
        this.magazineLoanConverter = magazineLoanConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public LibraryAccount convert(LibraryAccountCommand libraryAccountCommand) {
        if(libraryAccountCommand == null){
            return null;
        }

        final LibraryAccount loanLine = new LibraryAccount();

        loanLine.setId(libraryAccountCommand.getId());
        loanLine.setUser(libraryAccountCommand.getUser());

        if(libraryAccountCommand.getBookLoans() != null && libraryAccountCommand.getBookLoans().size() > 0){
            libraryAccountCommand.getBookLoans().forEach(bookLoan -> loanLine.getBookLoans().add(bookLoanConverter.convert(bookLoan)));
        }
        if(libraryAccountCommand.getMagazineLoans() != null && libraryAccountCommand.getMagazineLoans().size() > 0){
            libraryAccountCommand.getMagazineLoans().forEach(magazineLoan -> loanLine.getMagazineLoans().add(magazineLoanConverter.convert(magazineLoan)));
        }

        return loanLine;
    }
}

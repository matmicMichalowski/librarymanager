package matmic.librarymaneger.converters;

import lombok.Synchronized;
import matmic.librarymaneger.commands.LoanLineCommand;
import matmic.librarymaneger.model.LoanLine;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class LoanLineToLoanLineCommand implements Converter<LoanLine, LoanLineCommand>{

    private final BookLoanToBookLoanCommand bookLoanConverter;
    private final MagazineLoanToMagazineLoanCommand magazineLoanConverter;

    public LoanLineToLoanLineCommand(BookLoanToBookLoanCommand bookLoanConverter, MagazineLoanToMagazineLoanCommand magazineLoanToMagazineLoanCommand) {
        this.bookLoanConverter = bookLoanConverter;
        this.magazineLoanConverter = magazineLoanToMagazineLoanCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public LoanLineCommand convert(LoanLine loanLine) {
        if(loanLine == null){
            return null;
        }

        final LoanLineCommand loanLineCommand = new LoanLineCommand();

        loanLineCommand.setId(loanLine.getId());
        loanLineCommand.setUser(loanLine.getUser());

        if(loanLine.getBookLoans() != null && loanLine.getBookLoans().size() > 0){
            loanLine.getBookLoans().forEach( bookLoan -> loanLineCommand.getBookLoans().add(bookLoanConverter.convert(bookLoan)));
        }
        if(loanLine.getMagazineLoans() != null && loanLine.getMagazineLoans().size() > 0){
            loanLine.getMagazineLoans().forEach( magazineLoan -> loanLineCommand.getMagazineLoans().add(magazineLoanConverter.convert(magazineLoan)));
        }

        return loanLineCommand;
    }
}

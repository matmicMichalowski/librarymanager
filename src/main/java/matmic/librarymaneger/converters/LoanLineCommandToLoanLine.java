package matmic.librarymaneger.converters;

import lombok.Synchronized;
import matmic.librarymaneger.commands.LoanLineCommand;
import matmic.librarymaneger.model.LoanLine;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class LoanLineCommandToLoanLine implements Converter<LoanLineCommand, LoanLine>{

    private final BookLoanCommandToBookLoan bookLoanConverter;
    private final MagazineLoanCommandToMagazineLoan magazineLoanConverter;

    public LoanLineCommandToLoanLine(BookLoanCommandToBookLoan bookLoanConverter, MagazineLoanCommandToMagazineLoan magazineLoanConverter) {
        this.bookLoanConverter = bookLoanConverter;
        this.magazineLoanConverter = magazineLoanConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public LoanLine convert(LoanLineCommand loanLineCommand) {
        if(loanLineCommand == null){
            return null;
        }

        final LoanLine loanLine = new LoanLine();

        loanLine.setId(loanLineCommand.getId());
        loanLine.setUser(loanLineCommand.getUser());

        if(loanLineCommand.getBookLoans() != null && loanLineCommand.getBookLoans().size() > 0){
            loanLineCommand.getBookLoans().forEach( bookLoan -> loanLine.getBookLoans().add(bookLoanConverter.convert(bookLoan)));
        }
        if(loanLineCommand.getMagazineLoans() != null && loanLineCommand.getMagazineLoans().size() > 0){
            loanLineCommand.getMagazineLoans().forEach( magazineLoan -> loanLine.getMagazineLoans().add(magazineLoanConverter.convert(magazineLoan)));
        }

        return loanLine;
    }
}

package matmic.librarymaneger.converter;

import lombok.Synchronized;
import matmic.librarymaneger.command.LoanCommand;
import matmic.librarymaneger.model.Loan;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class LoanToLoanCommand implements Converter<Loan, LoanCommand>{

    @Synchronized
    @Nullable
    @Override
    public LoanCommand convert(Loan loan) {
        if(loan == null) {
            return null;
        }

        final LoanCommand loanCommand = new LoanCommand();

        loanCommand.setId(loan.getId());
        loanCommand.setItemId(loan.getItem().getId());
        loanCommand.setUserId(loan.getUser().getId());

        return loanCommand;
    }
}

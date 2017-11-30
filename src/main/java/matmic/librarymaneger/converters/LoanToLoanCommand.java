package matmic.librarymaneger.converters;

import lombok.Synchronized;
import matmic.librarymaneger.commands.LoanCommand;
import matmic.librarymaneger.model.Loan;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class LoanToLoanCommand implements Converter<Loan, LoanCommand>{

    private ItemToItemCommand itemToItemCommand;
    private final UserToUserCommand userToUserCommand;

    public LoanToLoanCommand(ItemToItemCommand itemToItemCommand, UserToUserCommand userToUserCommand) {
        this.itemToItemCommand = itemToItemCommand;
        this.userToUserCommand = userToUserCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public LoanCommand convert(Loan loan) {
        if (loan == null) {
            return null;
        }

        final LoanCommand loanCommand = new LoanCommand();

        loanCommand.setId(loan.getId());
        loanCommand.setUser(userToUserCommand.convert(loan.getUser()));
        loanCommand.setLoanDate(loan.getLoanDate());
        loanCommand.setItem(itemToItemCommand.convert(loan.getItem()));

        return loanCommand;
    }
}

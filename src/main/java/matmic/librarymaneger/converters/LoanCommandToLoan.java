package matmic.librarymaneger.converters;

import lombok.Synchronized;
import matmic.librarymaneger.commands.LoanCommand;
import matmic.librarymaneger.model.Loan;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class LoanCommandToLoan implements Converter<LoanCommand, Loan>{

    private final ItemCommandToItem itemCommandToItem;
    private final UserCommandToUser userCommandToUser;

    public LoanCommandToLoan(ItemCommandToItem itemCommandToItem, UserCommandToUser userCommandToUser) {
        this.itemCommandToItem = itemCommandToItem;
        this.userCommandToUser = userCommandToUser;
    }

    @Synchronized
    @Nullable
    @Override
    public Loan convert(LoanCommand loanCommand) {
        if (loanCommand == null) {
            return null;
        }

        final Loan loan = new Loan();

        loan.setId(loanCommand.getId());
        loan.setUser(userCommandToUser.convert(loanCommand.getUser()));
        loan.setLoanDate(loanCommand.getLoanDate());
        loan.setItem(itemCommandToItem.convert(loanCommand.getItem()));

        return loan;
    }

}

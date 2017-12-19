package matmic.librarymanager.converter;

import lombok.Synchronized;
import matmic.librarymanager.command.LoanCommand;
import matmic.librarymanager.model.Loan;
import matmic.librarymanager.services.ItemService;
import matmic.librarymanager.services.UserService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class LoanCommandToLoan implements Converter<LoanCommand, Loan> {

    private final ItemService itemService;
    private final UserService userService;



    public LoanCommandToLoan(ItemService itemService, UserService userService) {
        this.itemService = itemService;
        this.userService = userService;


    }

    @Synchronized
    @Nullable
    @Override
    public Loan convert(LoanCommand loanCommand) {
        if(loanCommand == null){
            return null;
        }

        final Loan loan = new Loan();


        loan.setId(loanCommand.getId());

        loan.setUser(userService.findUserById(loanCommand.getUserId()));
        loan.setItem(itemService.findItemById(loanCommand.getItemId()));

        return loan;
    }
}

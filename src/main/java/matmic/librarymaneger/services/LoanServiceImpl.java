package matmic.librarymaneger.services;

import matmic.librarymaneger.commands.LoanCommand;
import matmic.librarymaneger.converters.LoanCommandToLoan;
import matmic.librarymaneger.converters.LoanToLoanCommand;
import matmic.librarymaneger.model.Item;
import matmic.librarymaneger.model.Loan;
import matmic.librarymaneger.model.User;
import matmic.librarymaneger.model.enums.Availability;
import matmic.librarymaneger.repositories.ItemRepository;
import matmic.librarymaneger.repositories.LoanRepository;
import matmic.librarymaneger.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoanServiceImpl implements LoanService{

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final LoanCommandToLoan loanCommandToLoan;
    private final LoanRepository loanRepository;
    private final LoanToLoanCommand loanToCommand;


    public LoanServiceImpl(UserRepository userRepository, ItemRepository itemRepository, LoanCommandToLoan loanCommandToLoan, LoanRepository loanRepository, LoanToLoanCommand loanToCommand) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.loanCommandToLoan = loanCommandToLoan;
        this.loanRepository = loanRepository;
        this.loanToCommand = loanToCommand;
    }

    @Override
    public LoanCommand saveLoanCommand(LoanCommand loanCommand) {
        Optional<User> userOptional = userRepository.findById(loanCommand.getUser().getId());
        Optional<Item> itemOptional = itemRepository.findById(loanCommand.getItem().getId());

        if(!userOptional.isPresent() || !itemOptional.isPresent()){
            return new LoanCommand();
        }else {
//            User user = userOptional.get();
            Item item = itemOptional.get();

            Loan loan = loanCommandToLoan.convert(loanCommand);

//            loan.setUser(commandToUser.convert(loanCommand.getUser()));
            loan.setLoanDate();
            loan.setItem(item);
            item.setLoan(loan);
            item.setIsAvailable(Availability.BORROWED);
            itemRepository.save(item);

            return loanToCommand.convert(loan);
        }
    }

    @Override
    public void deleteLoanById(Long loanId) {

        Optional<Loan> loanOptional = loanRepository.findById(loanId);
        //Optional<User> userOptional = userRepository.findById(userId);
        if(loanOptional.isPresent()){ //userOptional.isPresent() &&
            //User user = userOptional.get();
            Loan toBeDeleted = loanOptional.get();

            Item item = toBeDeleted.getItem();

            item.setLoan(null);
            item.setIsAvailable(Availability.AVAILABLE);
            //user.getLoanLine().remove(toBeDeleted);
            loanRepository.delete(toBeDeleted);
           // userRepository.save(user);
            itemRepository.save(item);
        }
    }
}

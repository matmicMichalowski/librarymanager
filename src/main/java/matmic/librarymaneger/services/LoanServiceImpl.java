package matmic.librarymaneger.services;

import matmic.librarymaneger.command.LoanCommand;
import matmic.librarymaneger.converter.LoanCommandToLoan;
import matmic.librarymaneger.converter.LoanToLoanCommand;
import matmic.librarymaneger.model.Employee;
import matmic.librarymaneger.model.Item;
import matmic.librarymaneger.model.Loan;
import matmic.librarymaneger.model.User;
import matmic.librarymaneger.model.enums.Availability;
import matmic.librarymaneger.repositories.EmployeeRepository;
import matmic.librarymaneger.repositories.ItemRepository;
import matmic.librarymaneger.repositories.LoanRepository;
import matmic.librarymaneger.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoanServiceImpl implements LoanService{

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final EmployeeRepository employeeRepository;
    private final LoanRepository loanRepository;
    private final LoanCommandToLoan loanCommandToLoan;
    private final LoanToLoanCommand loanToLoanCommand;



    public LoanServiceImpl(UserRepository userRepository, ItemRepository itemRepository, EmployeeRepository employeeRepository, LoanRepository loanRepository, LoanCommandToLoan loanCommandToLoan, LoanToLoanCommand loanToLoanCommand) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.employeeRepository = employeeRepository;
        this.loanRepository = loanRepository;
        this.loanCommandToLoan = loanCommandToLoan;
        this.loanToLoanCommand = loanToLoanCommand;
    }

    @Override
    public LoanCommand saveLoan(LoanCommand loanCommand) {
        Optional<User> userOptional = userRepository.findById(loanCommand.getUserId());
        Optional<Item> itemOptional = itemRepository.findById(loanCommand.getItemId());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Employee employee = employeeRepository.findByEmail(username);

        if(!userOptional.isPresent() || !itemOptional.isPresent()){
            return new LoanCommand();
        }else {

            Item item = itemOptional.get();

            Loan loanToSave = loanCommandToLoan.convert(loanCommand);

            loanToSave.setItem(item);
            loanToSave.setUser(userOptional.get());
            loanToSave.setEmployee(employee);
            loanRepository.save(loanToSave);


            return loanToLoanCommand.convert(loanToSave);
        }
    }

    @Override
    public void deleteLoanById(Long loanId) {

        Optional<Loan> loanOptional = loanRepository.findById(loanId);

        if(loanOptional.isPresent()){
            Loan toBeDeleted = loanOptional.get();

            Item item = toBeDeleted.getItem();

            item.setLoan(null);
            item.setIsAvailable(Availability.AVAILABLE);

            loanRepository.delete(toBeDeleted);

            itemRepository.save(item);
        }
    }
}

package matmic.librarymanager.services;

import lombok.extern.slf4j.Slf4j;
import matmic.librarymanager.command.LoanCommand;
import matmic.librarymanager.converter.LoanCommandToLoan;
import matmic.librarymanager.converter.LoanToLoanCommand;
import matmic.librarymanager.model.Employee;
import matmic.librarymanager.model.Item;
import matmic.librarymanager.model.Loan;
import matmic.librarymanager.model.User;
import matmic.librarymanager.model.enums.Availability;
import matmic.librarymanager.repositories.EmployeeRepository;
import matmic.librarymanager.repositories.ItemRepository;
import matmic.librarymanager.repositories.LoanRepository;
import matmic.librarymanager.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
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


        if(userOptional.isPresent() && itemOptional.isPresent()){
            Item item = itemOptional.get();

            User user = userOptional.get();

            Loan loanToSave = loanCommandToLoan.convert(loanCommand);


            loanToSave.setItem(item);
            loanToSave.setUser(user);
            loanToSave.setEmployee(employee);
            loanRepository.save(loanToSave);


            return loanToLoanCommand.convert(loanToSave);

        }else {
            return new LoanCommand();
        }
    }

    @Override
    @Transactional
    public void updateLoansDeadline(){
        LocalDate actualDate = LocalDate.now();

        Set<Loan> loanSet = getLoans();

         loanSet.forEach(loan -> {
            if (loan.getLoanDeadline().compareTo(actualDate) < 0){
                loan.setBeforeDeadline(false);
            }
        });

         loanRepository.saveAll(loanSet);
    }


    @Override
    public Set<Loan> getLoans() {
        Set<Loan> loans = new HashSet<>();

        loanRepository.findAll().iterator().forEachRemaining(loans::add);
        return loans;
    }

    @Override
    public void deleteLoanById(Long loanId) {

        Optional<Loan> loanOptional = loanRepository.findById(loanId);

        if(loanOptional.isPresent()){
            Loan toBeDeleted = loanOptional.get();

            User user = toBeDeleted.getUser();
            user.getLoanLine().remove(toBeDeleted);

            Item item = toBeDeleted.getItem();
            item.setLoan(null);
            item.setIsAvailable(Availability.AVAILABLE);

            Employee employee = toBeDeleted.getEmployee();
            employee.getLoansByEmployee().remove(toBeDeleted);

            loanRepository.delete(toBeDeleted);

        }
    }
}

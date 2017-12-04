package matmic.librarymaneger.services;

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

    private final LoanRepository loanRepository;



    public LoanServiceImpl(UserRepository userRepository, ItemRepository itemRepository, LoanRepository loanRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.loanRepository = loanRepository;

    }

    @Override
    public Loan saveLoan(Loan loan) {
        Optional<User> userOptional = userRepository.findById(loan.getUser().getId());
        Optional<Item> itemOptional = itemRepository.findById(loan.getItem().getId());

        if(!userOptional.isPresent() || !itemOptional.isPresent()){
            return new Loan();
        }else {

            Item item = itemOptional.get();

            Loan loanToSave = loan;

            loan.setItem(item);

            loanRepository.save(loan);


            return loan;
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

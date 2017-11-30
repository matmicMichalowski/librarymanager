package matmic.librarymaneger.services;

import matmic.librarymaneger.commands.MagazineLoanCommand;
import matmic.librarymaneger.converters.MagazineLoanCommandToMagazineLoan;
import matmic.librarymaneger.converters.MagazineLoanToMagazineLoanCommand;
import matmic.librarymaneger.model.Availability;
import matmic.librarymaneger.model.Magazine;
import matmic.librarymaneger.model.MagazineLoan;
import matmic.librarymaneger.model.User;
import matmic.librarymaneger.repositories.MagazineLoanRepository;
import matmic.librarymaneger.repositories.MagazineRepository;
import matmic.librarymaneger.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MagazineLoanServiceImpl implements MagazineLoanService{

    private final UserRepository userRepository;
    private final MagazineRepository magazineRepository;
    private final MagazineLoanRepository magazineLoanRepository;
    private final MagazineLoanCommandToMagazineLoan magazineLoanCmdToMagazineLoan;
    private final MagazineLoanToMagazineLoanCommand magazineLoanToMagazineLoanCmd;

    public MagazineLoanServiceImpl(UserRepository userRepository, MagazineRepository magazineRepository, MagazineLoanRepository magazineLoanRepository, MagazineLoanCommandToMagazineLoan magazineLoanCmdToMagazineLoan, MagazineLoanToMagazineLoanCommand magazineLoanToMagazineLoanCmd) {
        this.userRepository = userRepository;
        this.magazineRepository = magazineRepository;
        this.magazineLoanRepository = magazineLoanRepository;
        this.magazineLoanCmdToMagazineLoan = magazineLoanCmdToMagazineLoan;
        this.magazineLoanToMagazineLoanCmd = magazineLoanToMagazineLoanCmd;
    }

    @Override
    @Transactional
    public MagazineLoanCommand saveMagazineLoanCommand(MagazineLoanCommand loanCommand) {
        Optional<User> userOptional = userRepository.findById(loanCommand.getLibraryAccountId());
        Optional<Magazine> magazineOptional = magazineRepository.findById(loanCommand.getMagazine().getId());

        if (!userOptional.isPresent() || !magazineOptional.isPresent()) {
            return new MagazineLoanCommand();
        } else {
            User user = userOptional.get();
            Magazine magazine = magazineOptional.get();

            MagazineLoan magazineLoan = magazineLoanCmdToMagazineLoan.convert(loanCommand);
            magazineLoan.setLibraryAccount(user.getUserLibraryAccount());
            magazineLoan.setMagazine(magazine);
            magazine.setMagazineLoan(magazineLoan);
            magazineLoan.setLoanDate();
            magazine.setIsAvailable(Availability.BORROWED);

            magazineRepository.save(magazine);
            userRepository.save(user);
            return magazineLoanToMagazineLoanCmd.convert(magazineLoan);
        }
    }



    @Override
        public void deleteLoanById(Long userId, Long loanId){

            Optional<User> userOptional = userRepository.findById(userId);
            Optional<MagazineLoan> bookLoanOptional = magazineLoanRepository.findById(loanId);

            if(userOptional.isPresent() && bookLoanOptional.isPresent()){
                User user = userOptional.get();
                MagazineLoan toBeDeleted = bookLoanOptional.get();

                Magazine magazine = toBeDeleted.getMagazine();

                toBeDeleted.setLibraryAccount(null);
                magazine.setMagazineLoan(null);
                magazine.setIsAvailable(Availability.AVAILABLE);
                user.getUserLibraryAccount().getBookLoans().remove(toBeDeleted);
                userRepository.save(user);
               magazineRepository.save(magazine);
            }
        }
}

package matmic.librarymaneger.services;

import matmic.librarymaneger.commands.BookLoanCommand;
import matmic.librarymaneger.converters.BookLoanCommandToBookLoan;
import matmic.librarymaneger.converters.BookLoanToBookLoanCommand;
import matmic.librarymaneger.model.Availability;
import matmic.librarymaneger.model.Book;
import matmic.librarymaneger.model.BookLoan;
import matmic.librarymaneger.model.User;
import matmic.librarymaneger.repositories.BookLoanRepository;
import matmic.librarymaneger.repositories.BookRepository;
import matmic.librarymaneger.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BookLoanServiceImpl implements BookLoanService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BookLoanCommandToBookLoan bookLoanCmdToBookLoan;
    private final BookLoanToBookLoanCommand bookLoanToBookLoanCmd;
    private final BookLoanRepository bookLoanRepository;

    public BookLoanServiceImpl(UserRepository userRepository, BookRepository bookRepository, BookLoanCommandToBookLoan bookLoanCmdToBookLoan, BookLoanToBookLoanCommand bookLoanToBookLoanCmd, BookLoanRepository bookLoanRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.bookLoanCmdToBookLoan = bookLoanCmdToBookLoan;
        this.bookLoanToBookLoanCmd = bookLoanToBookLoanCmd;
        this.bookLoanRepository = bookLoanRepository;
    }

    @Override
    @Transactional
    public BookLoanCommand saveBookLoanCommand(BookLoanCommand loanCommand) {
        Optional<User> userOptional = userRepository.findById(loanCommand.getLibraryAccountId());
        Optional<Book> bookOptional = bookRepository.findById(loanCommand.getBook().getId());

        if (!userOptional.isPresent() || !bookOptional.isPresent()) {
            return new BookLoanCommand();
        } else {
            User user = userOptional.get();
            Book book = bookOptional.get();

//            Optional<BookLoan> bookLoanOptional = user.getUserLibraryAccount().getBookLoans().stream()
//                    .filter(bookLoan -> bookLoan.getId().equals(loanCommand.getId()))
//                    .findFirst();
//
//            if (bookLoanOptional.isPresent()) {
//                BookLoan loanFound = bookLoanOptional.get();
//                loanFound.setLoanDate(loanCommand.getLoanDate());
//                loanFound.setBook(book);
//                loanFound.setLibraryAccount(user.getUserLibraryAccount());
//            } else {
                BookLoan bookLoan = bookLoanCmdToBookLoan.convert(loanCommand);
                bookLoan.setLibraryAccount(user.getUserLibraryAccount());
                bookLoan.setBook(book);
                book.setBookLoan(bookLoan);
                bookLoan.setLoanDate();
                book.setIsAvailable(Availability.BORROWED);

            bookRepository.save(book);
            userRepository.save(user);


//            Optional<BookLoan> savedBookLoanOptional = savedUser.getUserLibraryAccount().getBookLoans()
//                    .stream()
//                    .filter(userLoans -> userLoans.getId().equals(loanCommand.getId()))
//                    .findFirst();
//
//            if(!savedBookLoanOptional.isPresent()){
//                savedBookLoanOptional = savedUser.getUserLibraryAccount().getBookLoans()
//                        .stream()
//                        .filter(userLoans -> userLoans.getLibraryAccount().getId().equals(loanCommand.getLibraryAccountId()))
//                        .filter(userLoans -> userLoans.getBook().getId().equals(loanCommand.getBook().getId()))
//                        .filter(userLoans -> userLoans.getLoanDate().equals(loanCommand.getLoanDate()))
//                        .findFirst();
//            }
//            System.out.println("is that about me?");
            return bookLoanToBookLoanCmd.convert(bookLoan);
        }
    }

    @Override
    public void deleteLoanById(Long userId, Long loanId){

        Optional<User> userOptional = userRepository.findById(userId);
        Optional<BookLoan> bookLoanOptional = bookLoanRepository.findById(loanId);

        if(userOptional.isPresent() && bookLoanOptional.isPresent()){
            User user = userOptional.get();
            BookLoan toBeDeleted = bookLoanOptional.get();

            Book book = toBeDeleted.getBook();

            toBeDeleted.setLibraryAccount(null);
            book.setBookLoan(null);
            book.setIsAvailable(Availability.AVAILABLE);
            user.getUserLibraryAccount().getBookLoans().remove(toBeDeleted);
            userRepository.save(user);
            bookRepository.save(book);
        }
    }
}



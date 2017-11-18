package matmic.librarymaneger.converters;

import lombok.Synchronized;
import matmic.librarymaneger.commands.BookLoanCommand;
import matmic.librarymaneger.model.BookLoan;
import matmic.librarymaneger.model.LibraryAccount;
import matmic.librarymaneger.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class BookLoanCommandToBookLoan implements Converter<BookLoanCommand, BookLoan>{

    private BookCommandToBook bookConverter;


    public BookLoanCommandToBookLoan(BookCommandToBook bookConverter) {
        this.bookConverter = bookConverter;

    }

    @Synchronized
    @Nullable
    @Override
    public BookLoan convert(BookLoanCommand bookLoanCommand) {
        if(bookLoanCommand == null){
            return null;
        }

        final BookLoan bookLoan = new BookLoan();


        bookLoan.setBook(bookConverter.convert(bookLoanCommand.getBook()));
        bookLoan.setLoanDate(bookLoanCommand.getLoanDate());
        bookLoan.setId(bookLoanCommand.getId());
        if(bookLoanCommand.getLibraryAccountId() != null){
            User tempUser = new User();
            LibraryAccount acc = new LibraryAccount();
            tempUser.setUserLibraryAccount(acc);
            acc.setUser(tempUser);
            tempUser.setId(bookLoanCommand.getLibraryAccountId());
            bookLoan.setLibraryAccount(tempUser.getUserLibraryAccount());
            tempUser.getUserLibraryAccount().addBookLoan(bookLoan);


        }


        return bookLoan;
    }
}

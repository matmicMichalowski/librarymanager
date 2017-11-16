package matmic.librarymaneger.converters;

import lombok.Synchronized;
import matmic.librarymaneger.commands.BookLoanCommand;
import matmic.librarymaneger.model.BookLoan;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class BookLoanToBookLoanCommand implements Converter<BookLoan, BookLoanCommand>{

    private final BookToBookCommand bookConverter;


    public BookLoanToBookLoanCommand(BookToBookCommand bookConverter) {
        this.bookConverter = bookConverter;

    }

    @Synchronized
    @Nullable
    @Override
    public BookLoanCommand convert(BookLoan bookLoan) {
        if(bookLoan == null){
            return null;
        }

        final BookLoanCommand bookLoanCommand = new BookLoanCommand();

        bookLoanCommand.setBook(bookConverter.convert(bookLoan.getBook()));
        bookLoanCommand.setLoanDate(bookLoan.getLoanDate());
        bookLoanCommand.setId(bookLoan.getId());
        if(bookLoan.getLibraryAccount() != null){
            bookLoanCommand.setLibraryAccountId(bookLoan.getLibraryAccount().getId());
        }

        return bookLoanCommand;
    }
}

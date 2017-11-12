package matmic.librarymaneger.converters;

import lombok.Synchronized;
import matmic.librarymaneger.commands.BookLoanCommand;
import matmic.librarymaneger.model.BookLoan;
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


        return bookLoan;
    }
}

package matmic.librarymaneger.converters;

import lombok.Synchronized;
import matmic.librarymaneger.commands.BookCommand;
import matmic.librarymaneger.model.Book;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class BookToBookCommand implements Converter<Book, BookCommand>{


    @Synchronized
    @Nullable
    @Override
    public BookCommand convert(Book book) {
        if(book == null) {
            return null;
        }

        final BookCommand bookCommand = new BookCommand();

        bookCommand.setTitle(book.getTitle());
        bookCommand.setReleaseNumber(book.getReleaseNumber());
        bookCommand.setPublishYear(book.getPublishYear());
        bookCommand.setPublisher(book.getPublisher());
        bookCommand.setIsbn(book.getIsbn());
        bookCommand.setId(book.getId());
        bookCommand.setGenre(book.getGenre());
        bookCommand.setAuthor(book.getAuthor());
        bookCommand.setIsAvailable(book.getIsAvailable());
        bookCommand.setCoverImage(book.getCoverImage());

        return bookCommand;
    }
}

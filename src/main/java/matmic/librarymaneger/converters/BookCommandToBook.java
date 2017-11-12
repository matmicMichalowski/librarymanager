package matmic.librarymaneger.converters;

import lombok.Synchronized;
import matmic.librarymaneger.commands.BookCommand;
import matmic.librarymaneger.model.Book;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class BookCommandToBook implements Converter<BookCommand, Book>{


    @Synchronized
    @Nullable
    @Override
    public Book convert(BookCommand bookCommand) {
        if(bookCommand == null){
            return null;
        }

        final Book book = new Book();

        book.setIsAvailable(bookCommand.getIsAvailable());
        book.setTitle(bookCommand.getTitle());
        book.setReleaseNumber(bookCommand.getReleaseNumber());
        book.setPublishYear(bookCommand.getPublishYear());
        book.setPublisher(bookCommand.getPublisher());
        book.setIsbn(bookCommand.getIsbn());
        book.setId(bookCommand.getId());
        book.setGenre(bookCommand.getGenre());
        book.setAuthor(bookCommand.getAuthor());

        return book;
    }
}

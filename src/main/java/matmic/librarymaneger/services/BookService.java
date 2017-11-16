package matmic.librarymaneger.services;

import matmic.librarymaneger.commands.BookCommand;
import matmic.librarymaneger.model.Book;

import java.util.Set;

public interface BookService {

    BookCommand findCommandById(Long id);
    BookCommand saveBookCommand(BookCommand command);
    Set<Book> getBooks();
    Book findById(Long id);
    void deleteById(Long id);
}

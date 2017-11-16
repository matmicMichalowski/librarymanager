package matmic.librarymaneger.services;

import matmic.librarymaneger.commands.BookCommand;
import matmic.librarymaneger.converters.BookCommandToBook;
import matmic.librarymaneger.converters.BookToBookCommand;
import matmic.librarymaneger.model.Book;
import matmic.librarymaneger.repositories.BookLoanRepository;
import matmic.librarymaneger.repositories.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;
    private final BookCommandToBook commandToBook;
    private final BookToBookCommand bookToCommand;
    private final BookLoanRepository bookLoanRepository;

    public BookServiceImpl(BookRepository bookRepository, BookCommandToBook commandToBook, BookToBookCommand bookToCommand, BookLoanRepository bookLoanRepository) {
        this.bookRepository = bookRepository;
        this.commandToBook = commandToBook;
        this.bookToCommand = bookToCommand;
        this.bookLoanRepository = bookLoanRepository;
    }

    @Override
    @Transactional
    public BookCommand findCommandById(Long id) {

        return bookToCommand.convert(findById(id));
    }

    @Override
    public Book findById(Long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);

        if(!bookOptional.isPresent()){
            throw new RuntimeException("Oups");
        }
        return bookOptional.get();
    }

    @Override
    public BookCommand saveBookCommand(BookCommand command) {
       Book toSave = commandToBook.convert(command);
       Book savedBook = bookRepository.save(toSave);

        return bookToCommand.convert(savedBook);
    }

    @Override
    public Set<Book> getBooks() {
        Set<Book> books = new HashSet<>();

        bookRepository.findAll().iterator().forEachRemaining(books::add);
        return books;
    }

    @Override
    public void deleteById(Long bookId) {
//        Optional<Book> bookOptional = bookRepository.findById(bookId);
//        if(bookOptional.isPresent()){
//            Book book = bookOptional.get();
//            Optional<BookLoan> bookLoanOptional = bookLoanRepository.findById(book
//                    .getBookLoan()
//                    .getId());
//            if(bookLoanOptional.isPresent()){
//                BookLoan loanToDelete = bookLoanOptional.get();
//                loanToDelete.set
//            }
//        }
//        bookLoanRepository.deleteById(loanId);
        bookRepository.deleteById(bookId);
    }
}

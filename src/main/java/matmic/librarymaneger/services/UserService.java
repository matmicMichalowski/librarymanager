package matmic.librarymaneger.services;

import matmic.librarymaneger.commands.BookCommand;
import matmic.librarymaneger.commands.BookLoanCommand;
import matmic.librarymaneger.commands.LibraryAccountCommand;
import matmic.librarymaneger.commands.UserCommand;
import matmic.librarymaneger.model.User;

import java.util.Set;

public interface UserService {

    Set<User> getUsers();
    User findById(Long id);
    void deleteById(Long id);
    UserCommand findCommandById(Long id);
    UserCommand saveUserCommand(UserCommand userCommand);
    BookLoanCommand defineAndSaveNewLoan(LibraryAccountCommand account, BookCommand book);
}

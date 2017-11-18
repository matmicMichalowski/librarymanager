package matmic.librarymaneger.services;

import matmic.librarymaneger.commands.BookCommand;
import matmic.librarymaneger.commands.BookLoanCommand;
import matmic.librarymaneger.commands.LibraryAccountCommand;
import matmic.librarymaneger.commands.UserCommand;
import matmic.librarymaneger.converters.UserCommandToUser;
import matmic.librarymaneger.converters.UserToUserCommand;
import matmic.librarymaneger.model.LibraryAccount;
import matmic.librarymaneger.model.User;
import matmic.librarymaneger.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserToUserCommand userToUserCommand;
    private final UserCommandToUser userCommandToUser;

    public UserServiceImpl(UserRepository userRepository, UserToUserCommand userToUserCommand, UserCommandToUser userCommandToUser) {
        this.userRepository = userRepository;
        this.userToUserCommand = userToUserCommand;
        this.userCommandToUser = userCommandToUser;
    }


    @Override
    public Set<User> getUsers() {
        Set<User> users = new HashSet<>();

        userRepository.findAll().iterator().forEachRemaining(users::add);
        return users;
    }

    @Override
    public User findById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if(!userOptional.isPresent()){
            throw new RuntimeException("No such User");
        }

        return userOptional.get();
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public UserCommand findCommandById(Long id) {
        return userToUserCommand.convert(findById(id));
    }

    @Override
    @Transactional
    public UserCommand saveUserCommand(UserCommand userCommand) {
        System.out.println("user id " + userCommand.getId());
        User user = userCommandToUser.convert(userCommand);

        if(userCommand.getId() == null){
            LibraryAccount userAcc = new LibraryAccount();
            userAcc.setUser(user);
            user.setUserLibraryAccount(userAcc);
        }

        User savedUser = userRepository.save(user);

        return userToUserCommand.convert(savedUser);
    }

    @Override
    @Transactional
    public BookLoanCommand defineAndSaveNewLoan(LibraryAccountCommand account, BookCommand book){
        return null;
    }
}

package matmic.librarymaneger.services;

import matmic.librarymaneger.commands.UserCommand;
import matmic.librarymaneger.model.User;

import java.util.Set;

public interface UserService {

    Set<User> getUsers();
    User findById(Long id);
    void deleteById(Long id);
    UserCommand findCommandById(Long id);
}

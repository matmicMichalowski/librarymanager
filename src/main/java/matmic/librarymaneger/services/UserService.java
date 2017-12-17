package matmic.librarymaneger.services;


import matmic.librarymaneger.command.UserCommand;
import matmic.librarymaneger.model.User;

import java.util.Set;

public interface UserService {

    Set<User> getUsers();
    User findById(Long id);
    UserCommand findUserCommandById(Long id);
    void deleteById(Long id);
    User findUserById(Long id);
    UserCommand saveUser(UserCommand userToSave);
//    User saveUser(User user);
}

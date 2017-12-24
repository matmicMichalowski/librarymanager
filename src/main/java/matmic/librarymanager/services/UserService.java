package matmic.librarymanager.services;


import matmic.librarymanager.command.UserCommand;
import matmic.librarymanager.model.User;

import java.util.Set;

public interface UserService {
    User findUserByEmail(String email);
    Set<User> getUsers();
    User findById(Long id);
    UserCommand findUserCommandById(Long id);
    boolean deleteById(Long id);
    User findUserById(Long id);
    UserCommand saveUser(UserCommand userToSave);

}

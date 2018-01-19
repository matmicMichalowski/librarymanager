package matmic.librarymanager.services;


import matmic.librarymanager.command.UserCommand;
import matmic.librarymanager.converter.UserCommandToUser;
import matmic.librarymanager.converter.UserToUserCommand;
import matmic.librarymanager.model.User;
import matmic.librarymanager.repositories.UserRepository;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
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
    @Transactional(readOnly = true)
    public User findUserByEmail(String email) {
        Optional<User> optional = userRepository.findUserByEmail(email);
        if (optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<User> getUsers() {
        Set<User> users = new HashSet<>();

        userRepository.findAll().iterator().forEachRemaining(users::add);
        return users;
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if(!userOptional.isPresent()){
            throw new RuntimeException("No such User");
        }

        return userOptional.get();
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        Optional<User> userOptional = userRepository.findUserById(id);

        if(!userOptional.isPresent()){
            throw new NotFoundException("User not found.");
        }

        return userOptional.get();
    }

    @Override
    @Transactional(readOnly = true)
    public UserCommand findUserCommandById(Long id){
        return userToUserCommand.convert(findUserById(id));
    }

    @Override
    public UserCommand saveUser(UserCommand userToSave){
        User detachedUser = userCommandToUser.convert(userToSave);

        User savedUser = userRepository.save(detachedUser);
        return userToUserCommand.convert(savedUser);
    }

    @Override
    public boolean deleteById(Long id) {
        User userToDelete = findUserById(id);
        if(userToDelete.getLoanLine().size() > 0){
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }

}

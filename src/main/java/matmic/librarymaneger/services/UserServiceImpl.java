package matmic.librarymaneger.services;

import matmic.librarymaneger.commands.UserCommand;
import matmic.librarymaneger.converters.UserToUserCommand;
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

    public UserServiceImpl(UserRepository userRepository, UserToUserCommand userToUserCommand) {
        this.userRepository = userRepository;
        this.userToUserCommand = userToUserCommand;
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
}

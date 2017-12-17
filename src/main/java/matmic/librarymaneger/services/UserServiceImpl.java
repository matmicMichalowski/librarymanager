package matmic.librarymaneger.services;


import matmic.librarymaneger.Exceptions.NotFoundException;
import matmic.librarymaneger.command.UserCommand;
import matmic.librarymaneger.converter.UserCommandToUser;
import matmic.librarymaneger.converter.UserToUserCommand;
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
    public User findUserById(Long id) {
        Optional<User> userOptional = userRepository.findUserById(id);

        if(!userOptional.isPresent()){
            throw new NotFoundException("User not found.");
        }

        return userOptional.get();
    }

    @Override
    @Transactional
    public UserCommand findUserCommandById(Long id){
        return userToUserCommand.convert(findUserById(id));
    }

    @Override
    @Transactional
    public UserCommand saveUser(UserCommand userToSave){
        User detachedUser = userCommandToUser.convert(userToSave);

        User savedUser = userRepository.save(detachedUser);
        return userToUserCommand.convert(savedUser);
    }

//    @Override
//    @Transactional
//    public User saveUser(User user) {
//
//        userRepository.save(user);
//
//        return user;
//    }


}

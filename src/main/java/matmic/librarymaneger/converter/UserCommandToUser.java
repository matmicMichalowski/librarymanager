package matmic.librarymaneger.converter;

import lombok.Synchronized;
import matmic.librarymaneger.command.UserCommand;
import matmic.librarymaneger.model.User;
import matmic.librarymaneger.repositories.UserRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserCommandToUser implements Converter<UserCommand, User>{

    public UserCommandToUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final UserRepository userRepository;


    @Synchronized
    @Nullable
    @Override
    public User convert(UserCommand userCommand) {
        if (userCommand == null) {
            return null;
        }

        Optional<User> userOptional = userRepository.findUserById(userCommand.getId());
        User user;


        if(userOptional.isPresent()){
            user = userOptional.get();
        }else{
            user = new User();
        }

        user.setId(userCommand.getId());
        user.setEmail(userCommand.getEmail());
        user.setFirstName(userCommand.getFirstName());
        user.setLastName(userCommand.getLastName());
        user.setPhoneNumber(userCommand.getPhoneNumber());
        user.setPostCode(userCommand.getPostCode());
        user.setAddress(userCommand.getAddress());
        user.setCity(userCommand.getCity());

        return user;
    }
}

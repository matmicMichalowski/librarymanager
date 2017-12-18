package matmic.librarymanager.converter;

import lombok.Synchronized;
import matmic.librarymanager.command.UserCommand;
import matmic.librarymanager.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UserToUserCommand implements Converter<User, UserCommand>{

    @Synchronized
    @Nullable
    @Override
    public UserCommand convert(User user) {
        if(user == null) {
            return null;
        }

        final UserCommand userCommand = new UserCommand();

        userCommand.setId(user.getId());
        userCommand.setEmail(user.getEmail());
        userCommand.setFirstName(user.getFirstName());
        userCommand.setLastName(user.getLastName());
        userCommand.setPhoneNumber(user.getPhoneNumber());
        userCommand.setPostCode(user.getPostCode());
        userCommand.setAddress(user.getAddress());
        userCommand.setCity(user.getCity());


        return userCommand;
    }
}

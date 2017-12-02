package matmic.librarymaneger.converters;

import lombok.Synchronized;
import matmic.librarymaneger.commands.UserCommand;
import matmic.librarymaneger.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UserCommandToUser implements Converter<UserCommand, User> {



    @Synchronized
    @Nullable
    @Override
    public User convert(UserCommand userCommand) {
        if(userCommand == null) {
            return null;
        }

        final User user = new User();

        user.setId(userCommand.getId());
        user.setPostCode(userCommand.getPostCode());
        user.setCity(userCommand.getCity());
        user.setAddress(userCommand.getAddress());
        user.setPhoneNumber(userCommand.getPhoneNumber());
        user.setFirstName(userCommand.getFirstName());
        user.setLastName(userCommand.getLastName());
        user.setEmail(userCommand.getEmail());

        return user;
    }
}

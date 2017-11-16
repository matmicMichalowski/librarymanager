package matmic.librarymaneger.converters;

import lombok.Synchronized;
import matmic.librarymaneger.commands.UserCommand;
import matmic.librarymaneger.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;



@Component
public class UserToUserCommand implements Converter<User, UserCommand>{

    private final LibraryAccountToLibraryAccountCommand loanLineConverter;

    public UserToUserCommand(LibraryAccountToLibraryAccountCommand loanLineCommand) {
        this.loanLineConverter = loanLineCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public UserCommand convert(User user) {
        if(user == null) {
            return null;
        }

        final UserCommand userCommand = new UserCommand();


        userCommand.setId(user.getId());
        userCommand.setPostCode(user.getPostCode());
        userCommand.setCity(user.getCity());
        userCommand.setAddress(user.getAddress());
        userCommand.setPhoneNumber(user.getPhoneNumber());
        userCommand.setFirstName(user.getFirstName());
        userCommand.setLastName(user.getLastName());
        userCommand.setEmail(user.getEmail());
        userCommand.setLibraryAccountCommand(loanLineConverter.convert(user.getUserLibraryAccount()));

        return userCommand;
    }
}
